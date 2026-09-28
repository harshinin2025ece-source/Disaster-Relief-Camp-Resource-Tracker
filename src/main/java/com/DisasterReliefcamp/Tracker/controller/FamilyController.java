package com.DisasterReliefcamp.Tracker.controller;

import com.DisasterReliefcamp.Tracker.entity.Family;
import com.DisasterReliefcamp.Tracker.service.FamilyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/families")
@CrossOrigin
public class FamilyController {

    private final FamilyService familyService;

    public FamilyController(FamilyService familyService) {
        this.familyService = familyService;
    }

    @PostMapping
    public ResponseEntity<Family> registerFamily(
            @RequestBody Family family) {

        return new ResponseEntity<>(
                familyService.registerFamily(family),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<Family>> getAllFamilies() {

        return ResponseEntity.ok(
                familyService.getAllFamilies()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Family> getFamily(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                familyService.getFamilyById(id)
        );
    }

    @GetMapping("/camp/{campId}")
    public ResponseEntity<List<Family>> getFamiliesByCamp(
            @PathVariable Long campId) {

        return ResponseEntity.ok(
                familyService.getFamiliesByCamp(campId)
        );
    }
    // Add this inside FamilyController.java
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFamily(@PathVariable Long id) {
        familyService.deleteFamily(id);
        return ResponseEntity.noContent().build();
    }
}