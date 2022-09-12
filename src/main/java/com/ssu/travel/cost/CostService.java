package com.ssu.travel.cost;


import com.ssu.travel.common.code.ErrorCode;
import com.ssu.travel.common.exception.RecordNotFoundException;
import com.ssu.travel.cost.dto.request.CostCreateRequest;
import com.ssu.travel.cost.dto.response.CostCreateResponse;
import com.ssu.travel.cost.dto.response.CostResponse;
import com.ssu.travel.security.oauth2.KakaoApiService;
import com.ssu.travel.travel.Travel;
import com.ssu.travel.travel.TravelRepository;
import com.ssu.travel.cost.dto.response.SimpleCostResponse;
import com.ssu.travel.user.User;
import com.ssu.travel.user.UserRepository;
import com.ssu.travel.usercost.UserCost;
import com.ssu.travel.usercost.UserCostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CostService {

    private final UserRepository userRepository;

    private final UserCostRepository userCostRepository;

    private final CostRepository costRepository;

    private final TravelRepository travelRepository;

    private final KakaoApiService kakaoApiService;

    public CostCreateResponse createCost(CostCreateRequest request) {
        // 여행 조회
        Travel travel = getTravelOrException(request.getTravelId());

        // 결제자 조회
        User payer = getUserOrException(request.getPayerId());

        // 비용 entity save
        Cost cost = costRepository.save(Cost.of(travel, request.getTotalAmount(), payer.getId(), request.getTitle(), request.getContent()));

        // 사용자_비용(경비) 객체 생성
        for (Long userId : request.getAmountsPerUser().keySet()) {
            User user = getUserOrException(userId);

            UserCost userCost = UserCost.of(user, cost, request.getAmountsPerUser().get(userId));

            // cascade save
            cost.addUserCost(userCost);
        }

        costRepository.save(cost);

        return CostCreateResponse.from(cost, payer);
    }

    @Transactional(readOnly = true)
    public CostResponse getCostById(Long costId) {
        Cost cost = costRepository.getCostById(costId)
                .orElseThrow(() -> new RuntimeException("해당 지출을 찾을 수 없습니다."));

        return CostResponse.from(cost);
    }

    @Transactional(readOnly = true)
    public Page<SimpleCostResponse> getCosts(Pageable pageable) {
        return costRepository.findAll(pageable).map(SimpleCostResponse::from);
    }


    private Travel getTravelOrException(Long travelId) {
        return travelRepository.findById(travelId)
                .orElseThrow(() -> new RuntimeException("여행이 존재하지 않습니다."));
    }

    private User getUserOrException(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RecordNotFoundException("해당 ID의 User가 존재하지 않습니다.", ErrorCode.USER_NOT_FOUND));
    }
}
