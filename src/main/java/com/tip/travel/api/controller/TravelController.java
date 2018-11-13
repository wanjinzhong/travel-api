package com.tip.travel.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tip.travel.api.utils.JsonEntity;
import com.tip.travel.api.utils.ResponseHelper;
import com.tip.travel.common.domain.Travel;
import com.tip.travel.common.service.TravelService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/public/api")
public class TravelController {
    @Reference
    private TravelService travelService;

    @GetMapping("/travel/{id}")
    public JsonEntity<Travel> getTravelById(@PathVariable("id") Long id) {
        return ResponseHelper.createInstance(travelService.getTravelById(id));
    }

    @PostMapping("/travel")
    public JsonEntity<Integer> createNewTravel(@RequestBody Travel travel) {
        return ResponseHelper.createInstance(travelService.saveTravel(travel));
    }

    @DeleteMapping("/travel/{id}")
    public JsonEntity<Integer> deleteTravel(@PathVariable("id") Long id) {
        return ResponseHelper.createInstance(travelService.deleteTravel(id));
    }

}
