package com.DisasterReliefcamp.Tracker.controller;

import com.DisasterReliefcamp.Tracker.entity.Distribution;
import com.DisasterReliefcamp.Tracker.service.DistributionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/distributions")
@CrossOrigin
public class DistributionController {

    private final DistributionService distributionService;

    public DistributionController(
            DistributionService distributionService) {

        this.distributionService = distributionService;
    }

    @PostMapping
    public ResponseEntity<Distribution> distributeSupply(
            @RequestBody Distribution distribution) {

        return new ResponseEntity<>(
                distributionService.distributeSupply(distribution),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<Distribution>> getAllDistributions() {

        return ResponseEntity.ok(
                distributionService.getAllDistributions()
        );
    }
}