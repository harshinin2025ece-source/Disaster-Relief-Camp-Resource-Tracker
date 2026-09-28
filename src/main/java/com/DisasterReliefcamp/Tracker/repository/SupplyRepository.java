package com.DisasterReliefcamp.Tracker.repository;

import com.DisasterReliefcamp.Tracker.entity.Supply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface SupplyRepository extends JpaRepository<Supply, Long> {

    Optional<Supply> findByCampIdAndType(Long campId, String type);

    List<Supply> findByCampId(Long campId);
}