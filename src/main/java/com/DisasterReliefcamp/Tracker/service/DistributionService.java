package com.DisasterReliefcamp.Tracker.service;

import com.DisasterReliefcamp.Tracker.entity.Distribution;
import com.DisasterReliefcamp.Tracker.entity.Supply;
import com.DisasterReliefcamp.Tracker.repository.DistributionRepository;
import com.DisasterReliefcamp.Tracker.repository.SupplyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DistributionService {

    private final DistributionRepository distributionRepository;
    private final SupplyRepository supplyRepository;

    public DistributionService(DistributionRepository distributionRepository, SupplyRepository supplyRepository) {
        this.distributionRepository = distributionRepository;
        this.supplyRepository = supplyRepository;
    }

    @Transactional
    public Distribution distributeSupply(Distribution distribution) {
        Supply supply = supplyRepository.findById(distribution.getSupply().getId())
                .orElseThrow(() -> new RuntimeException("Supply not found with id: " + distribution.getSupply().getId()));

        // Enforce Business Rule: Check Inventory Levels
        if (distribution.getQuantity() > supply.getQuantity()) {
            throw new IllegalStateException("Not enough supplies! Requested: " + distribution.getQuantity()
                    + ", Available: " + supply.getQuantity());
        }

        // Deduct from inventory and save
        supply.setQuantity(supply.getQuantity() - distribution.getQuantity());
        supplyRepository.save(supply);

        // System notification idea from the question paper
        System.out.println("NOTIFICATION: Distributed " + distribution.getQuantity() + " " + supply.getName() + " to family ID " + distribution.getFamily().getId());

        return distributionRepository.save(distribution);
    }

    public List<Distribution> getAllDistributions() {
        return distributionRepository.findAll();
    }
}