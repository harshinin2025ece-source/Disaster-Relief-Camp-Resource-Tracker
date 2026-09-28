package com.DisasterReliefcamp.Tracker.entity;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "families")
public class Family {
    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Distribution> distributions;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String headName;

    private String contactNumber;

    private Integer headcount;

    @ManyToOne
    @JoinColumn(name = "camp_id")
    private Camp camp;

    public Family() {
    }

    public Family(String headName, String contactNumber, Integer headcount) {
        this.headName = headName;
        this.contactNumber = contactNumber;
        this.headcount = headcount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Integer getHeadcount() {
        return headcount;
    }

    public void setHeadcount(Integer headcount) {
        this.headcount = headcount;
    }

    public Camp getCamp() {
        return camp;
    }

    public void setCamp(Camp camp) {
        this.camp = camp;
    }
}