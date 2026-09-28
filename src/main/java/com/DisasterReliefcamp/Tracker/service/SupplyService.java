package com.DisasterReliefcamp.Tracker.service;

import com.DisasterReliefcamp.Tracker.entity.Supply;
import com.DisasterReliefcamp.Tracker.repository.SupplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplyService {

    private final SupplyRepository supplyRepository;

    public SupplyService(SupplyRepository supplyRepository) {
        this.supplyRepository = supplyRepository;
    }

    public Supply addSupply(Supply supply) {
        return supplyRepository.save(supply);
    }

    public List<Supply> getAllSupplies() {
        return supplyRepository.findAll();
    }

    public Supply getSupplyById(Long id) {
        return supplyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supply not found with id: " + id));
    }

    public List<Supply> getSuppliesByCamp(Long campId) {
        // Filters supplies manually to avoid requiring custom Repository queries
        return supplyRepository.findAll().stream()
                .filter(supply -> supply.getCamp() != null && supply.getCamp().getId().equals(campId))
                .collect(Collectors.toList());
    }

    public void deleteSupply(Long id) {
        if (!supplyRepository.existsById(id)) {
            throw new RuntimeException("Supply log not found with id: " + id);
        }
        supplyRepository.deleteById(id);
    }
}