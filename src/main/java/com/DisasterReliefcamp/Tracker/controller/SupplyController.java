package com.DisasterReliefcamp.Tracker.controller;

import com.DisasterReliefcamp.Tracker.entity.Supply;
import com.DisasterReliefcamp.Tracker.service.SupplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/supplies")
@CrossOrigin
public class SupplyController {

    private final SupplyService supplyService;

    public SupplyController(SupplyService supplyService) {
        this.supplyService = supplyService;
    }

    @PostMapping
    public ResponseEntity<Supply> addSupply(
            @Valid @RequestBody Supply supply) {

        return new ResponseEntity<>(
                supplyService.addSupply(supply),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<Supply>> getAllSupplies() {

        return ResponseEntity.ok(
                supplyService.getAllSupplies()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supply> getSupply(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                supplyService.getSupplyById(id)
        );
    }

    @GetMapping("/camp/{campId}")
    public ResponseEntity<List<Supply>> getSuppliesByCamp(
            @PathVariable Long campId) {

        return ResponseEntity.ok(
                supplyService.getSuppliesByCamp(campId)
        );
    }
    // Add this inside SupplyController.java
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupply(@PathVariable Long id) {
        supplyService.deleteSupply(id);
        return ResponseEntity.noContent().build();
    }
}