package com.DisasterReliefcamp.Tracker.service;

import com.DisasterReliefcamp.Tracker.entity.Camp;
import com.DisasterReliefcamp.Tracker.entity.Family;
import com.DisasterReliefcamp.Tracker.repository.CampRepository;
import com.DisasterReliefcamp.Tracker.repository.FamilyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FamilyService {

    private final FamilyRepository familyRepository;
    private final CampRepository campRepository;

    public FamilyService(FamilyRepository familyRepository, CampRepository campRepository) {
        this.familyRepository = familyRepository;
        this.campRepository = campRepository;
    }

    @Transactional
    public Family registerFamily(Family family) {
        Camp camp = campRepository.findById(family.getCamp().getId())
                .orElseThrow(() -> new RuntimeException("Camp not found with id: " + family.getCamp().getId()));

        int current = camp.getCurrentOccupancy() == null ? 0 : camp.getCurrentOccupancy();
        int remainingCapacity = camp.getCapacity() - current;

        // Enforce Business Rule: Check Camp Capacity
        if (family.getHeadcount() > remainingCapacity) {
            throw new IllegalStateException("Camp capacity exceeded! Cannot register this family. Remaining capacity: " + remainingCapacity);
        }

        // Update and save the new camp occupancy
        camp.setCurrentOccupancy(current + family.getHeadcount());
        campRepository.save(camp);

        return familyRepository.save(family);
    }

    public List<Family> getAllFamilies() {
        return familyRepository.findAll();
    }

    public Family getFamilyById(Long id) {
        return familyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Family not found with id: " + id));
    }

    public List<Family> getFamiliesByCamp(Long campId) {
        return familyRepository.findAll().stream()
                .filter(family -> family.getCamp() != null && family.getCamp().getId().equals(campId))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteFamily(Long id) {
        Family family = familyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Family not found with id: " + id));

        Camp camp = family.getCamp();

        // Free up the space in the camp when a family is deleted
        if (camp != null) {
            int current = camp.getCurrentOccupancy() == null ? 0 : camp.getCurrentOccupancy();
            camp.setCurrentOccupancy(Math.max(0, current - family.getHeadcount()));
            campRepository.save(camp);
        }

        familyRepository.deleteById(id);
    }
}