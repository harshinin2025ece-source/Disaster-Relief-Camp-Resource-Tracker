package com.DisasterReliefcamp.Tracker.controller;

import com.DisasterReliefcamp.Tracker.entity.Camp;
import com.DisasterReliefcamp.Tracker.service.CampService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/camps")
@CrossOrigin
public class CampController {

    private final CampService campService;

    public CampController(CampService campService) {
        this.campService = campService;
    }

    @PostMapping
    public ResponseEntity<Camp> createCamp(@RequestBody Camp camp) {

        return new ResponseEntity<>(
                campService.createCamp(camp),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<Camp>> getAllCamps() {

        return ResponseEntity.ok(
                campService.getAllCamps()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Camp> getCamp(@PathVariable Long id) {

        return ResponseEntity.ok(
                campService.getCampById(id)
        );
    }
}