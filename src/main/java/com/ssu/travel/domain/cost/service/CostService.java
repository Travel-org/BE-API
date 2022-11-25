package com.ssu.travel.domain.cost.service;


import com.ssu.travel.domain.cost.web.request.CalculateCostRequest;
import com.ssu.travel.domain.cost.web.request.CreateCostRequest;
import com.ssu.travel.domain.cost.dto.CostDto;
import com.ssu.travel.domain.cost.dto.SimpleCostDto;
import com.ssu.travel.domain.cost.entity.Cost;
import com.ssu.travel.domain.cost.exception.CostNotFoundException;
import com.ssu.travel.domain.cost.repository.CostRepository;
import com.ssu.travel.domain.cost.web.request.UpdateCostRequest;
import com.ssu.travel.domain.email.service.CustomMailSender;
import com.ssu.travel.domain.travel.entity.Travel;
import com.ssu.travel.domain.travel.service.TravelService;
import com.ssu.travel.domain.user.entity.User;
import com.ssu.travel.domain.user.service.UserService;
import com.ssu.travel.domain.usercost.entity.UserCost;
import com.ssu.travel.domain.usercost.exception.UserCostNotFoundException;
import com.ssu.travel.domain.usercost.repository.UserCostRepository;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CostService {

    private final CostRepository costRepository;
    private final UserCostRepository userCostRepository;
    private final UserService userService;
    private final TravelService travelService;
    private final CustomMailSender customMailSender;

    @PersistenceContext
    private final EntityManager em;

    @Transactional
    public CostDto saveCost(Long travelId, CreateCostRequest request) {
        Travel travel = travelService.findTravelEntityById(travelId);
        User payer = userService.findUserEntityById(request.getPayerId());

        Cost cost = costRepository.save(
                Cost.of(travel,
                        request.getTotalAmount(),
                        payer.getId(),
                        request.getTitle(),
                        request.getContent()));

        for (Long userId : request.getAmountsPerUser().keySet()) {
            User user = userService.findUserEntityById(userId);
            UserCost userCost = UserCost.of(user,
                    cost,
                    request.getAmountsPerUser().get(userId));
            // cascade save
            cost.addUserCost(userCost);
        }
        costRepository.save(cost);

        return CostDto.from(cost);
    }

    @Transactional
    public CostDto updateCost(Long travelId, Long costId, UpdateCostRequest request) {
        Cost cost = findCostEntityById(travelId, costId);

        // cost update
        cost.update(request.getTotalAmount(),
                request.getTitle(),
                request.getContent(),
                request.getPayerId());

        // userCost update
        Cost updatedCost = updateUserCost(cost, request);

        return CostDto.from(updatedCost);
    }

    // TODO: 이메일로 해결해야함 이메일로 보내고 어떻게 해결할지 고민이 필요
    @Transactional
    public void calculateUserCost(User user, Long userCostId, CalculateCostRequest request) {
        UserCost userCost = findUserCostEntityById(userCostId);
        User userEntity = userService.findUserEntityById(user.getId());

        String text = "MTravel\n" +
                "안녕하세요 " + userCost.getUser().getNickname() + "님!\n" +
                userEntity.getNickname() + " 님으로 부터\n" +
                userCost.getAmount() + "원 정산이 요청되었습니다!";

        for (String email : request.getEmails()) {
            customMailSender.sendCalculationEmail(email, text);
        }
        userCost.isRequested();
    }

    public CostDto getCostById(Long travelId, Long costId) {
        Cost cost = costRepository.findCostWithUserByTravelIdAndCostId(travelId, costId)
                .orElseThrow(() -> new CostNotFoundException("해당 경비(여행 비용)를 찾을 수 없습니다."));

        return CostDto.from(cost);
    }

    public Page<SimpleCostDto> getCosts(Pageable pageable) {
        return costRepository.findAll(pageable)
                .map(SimpleCostDto::from);
    }

    public void deleteCostById(Long travelId, Long costId) {
        Cost cost = findCostEntityById(travelId, costId);
        costRepository.delete(cost);
    }


    // update UserCost
    private Cost updateUserCost(Cost cost, UpdateCostRequest request) {
        List<UserCost> userCosts = cost.getUserCosts();
        if (!request.getAmountsPerUser().keySet().isEmpty()) {
            Map<Long, UserCost> amountsPerUser = new HashMap<>();
            userCosts.forEach(userCost ->
                    amountsPerUser.put(userCost.getUser().getId(), userCost)
            );
            Set<Long> userToDelete = new HashSet<>(amountsPerUser.keySet());
            Set<Long> userToAdd = new HashSet<>(request.getAmountsPerUser().keySet());
            Set<Long> userToStay = new HashSet<>(userToAdd);

            userToStay.retainAll(userToDelete);
            userToDelete.removeAll(userToAdd);
            userToAdd.removeAll(userToStay);

            userToDelete.forEach(userId ->
                    userCostRepository.deleteByUserIdAndCostId(userId, cost.getId()));

            userToAdd.forEach(userId -> userCostRepository.save(UserCost.of(
                    userService.findUserEntityById(userId),
                    cost,
                    request.getAmountsPerUser().get(userId))
            ));

            userToStay.forEach(userId -> userCostRepository.updateAmountById(
                    request.getAmountsPerUser().get(userId),
                    amountsPerUser.get(userId).getId()
            ));
        }
        // TODO: em.flush, em.clear() 밖에 반영된 Cost가져오는 것일까? jpa 캐시 좀 더 고민해보기
        em.flush();
        em.clear();
        return findCostEntityById(cost.getId());
    }

    // == Entity 조회 메서드 ==
    public Cost findCostEntityById(Long costId) {
        return costRepository.findById(costId)
                .orElseThrow(() -> new CostNotFoundException("해당 경비(여행 비용)를 찾을 수 없습니다."));
    }

    public Cost findCostEntityById(Long travelId, Long costId) {
        return costRepository.findCostByTravelIdAndCostId(travelId, costId)
                .orElseThrow(() -> new CostNotFoundException("해당 경비(여행 비용)를 찾을 수 없습니다."));
    }

    public UserCost findUserCostEntityById(Long userCostId) {
        return userCostRepository.findById(userCostId)
                .orElseThrow(() -> new UserCostNotFoundException("해당 정산 요청을 찾을 수 없습니다."));
    }
}
