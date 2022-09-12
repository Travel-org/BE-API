package com.ssu.travel.cost;

import com.ssu.travel.cost.dto.request.CostCreateRequest;
import com.ssu.travel.cost.dto.response.CostCreateResponse;
import com.ssu.travel.cost.dto.response.CostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/v1/costs")
@RestController
public class CostController {
    private final CostService costService;

    @PostMapping
    public CostCreateResponse createCost(@RequestBody @Valid CostCreateRequest request) {
        return costService.createCost(request);
    }

    @GetMapping("/{costId}")
    public CostResponse getCost(@PathVariable Long costId) {
        return this.costService.getCostById(costId);
    }
}
