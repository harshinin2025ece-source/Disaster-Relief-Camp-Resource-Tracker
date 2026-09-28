package com.DisasterReliefcamp.Tracker.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "camps")
public class Camp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String location;

    private Integer capacity;
    private Integer currentOccupancy = 0;
    public Camp() {
    }

    public Camp(String name, String location, Integer capacity) {
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.currentOccupancy = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getCurrentOccupancy() {
        return currentOccupancy;
    }

    public void setCurrentOccupancy(Integer currentOccupancy) {
        this.currentOccupancy = currentOccupancy;
    }
}