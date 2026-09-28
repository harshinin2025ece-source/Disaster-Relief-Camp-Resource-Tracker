package com.DisasterReliefcamp.Tracker.service;

import com.DisasterReliefcamp.Tracker.entity.Camp;
import com.DisasterReliefcamp.Tracker.repository.CampRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampService {

    private final CampRepository campRepository;

    public CampService(CampRepository campRepository) {
        this.campRepository = campRepository;
    }

    public Camp createCamp(Camp camp) {

        if (camp.getCapacity() <= 0) {
            throw new RuntimeException("Camp capacity must be greater than zero");
        }

        camp.setCurrentOccupancy(0);

        return campRepository.save(camp);
    }

    public List<Camp> getAllCamps() {
        return campRepository.findAll();
    }

    public Camp getCampById(Long id) {
        return campRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Camp not found with ID: " + id));
    }
}