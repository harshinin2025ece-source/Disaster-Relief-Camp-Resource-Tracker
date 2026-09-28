package com.DisasterReliefcamp.Tracker.repository;
import  com.DisasterReliefcamp.Tracker.entity.Distribution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistributionRepository extends JpaRepository<Distribution, Long> {
}