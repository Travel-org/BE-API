package com.ssu.travel.domain.cost.web;

import com.ssu.travel.domain.cost.dto.CostDto;
import com.ssu.travel.domain.cost.service.CostService;
import com.ssu.travel.domain.cost.web.request.CalculateCostRequest;
import com.ssu.travel.domain.cost.web.request.CreateCostRequest;
import com.ssu.travel.domain.cost.web.request.UpdateCostRequest;
import com.ssu.travel.domain.user.entity.User;
import com.ssu.travel.global.dto.Result;
import com.ssu.travel.global.security.annotation.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CostController {

    private final CostService costService;

    @PostMapping("/api/travel/{travelId}/cost")
    public ResponseEntity saveCost(@PathVariable Long travelId,
                                   @RequestBody @Validated CreateCostRequest request) {
        CostDto costDto = costService.saveCost(travelId, request);
        return ResponseEntity.ok(Result.createSuccessResult(costDto));
    }

    @PutMapping("/api/travel/{travelId}/cost/{costId}")
    public ResponseEntity updateCost(@PathVariable Long travelId,
                                     @PathVariable Long costId,
                                     @RequestBody @Validated UpdateCostRequest request) {
        CostDto costDto = costService.updateCost(travelId, costId, request);
        return ResponseEntity.ok(Result.createSuccessResult(costDto));
    }

    @GetMapping("/api/travel/{travelId}/cost/{costId}")
    public ResponseEntity getCost(@PathVariable Long travelId,
                                  @PathVariable Long costId) {
        CostDto costDto = costService.getCostById(travelId, costId);
        return ResponseEntity.ok(Result.createSuccessResult(costDto));
    }

    @DeleteMapping("/api/travel/{travelId}/cost/{costId}")
    public ResponseEntity deleteCost(@PathVariable Long travelId,
                                     @PathVariable Long costId) {
        costService.deleteCostById(travelId, costId);
        return ResponseEntity.ok(Result.createSuccessResult(costId));
    }

    // TODO: 프론트랑 어떻게 해결할지 얘기 필요
    @PatchMapping("/api/travel/{travelId}/cost/{costId}/userCost/{userCostId}")
    public ResponseEntity calculateUserCost(@LoginUser User user,
                                            @PathVariable Long travelId,
                                            @PathVariable Long costId,
                                            @PathVariable Long userCostId,
                                            @RequestBody @Validated CalculateCostRequest request) {
        costService.calculateUserCost(user, userCostId, request);
        return ResponseEntity.ok(Result.createSuccessResult(null));
    }
}
