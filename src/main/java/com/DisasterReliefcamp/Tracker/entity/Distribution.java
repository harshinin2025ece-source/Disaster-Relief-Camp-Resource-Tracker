package com.DisasterReliefcamp.Tracker.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "distributions")
public class Distribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "camp_id")
    private Camp camp;

    @ManyToOne
    @JoinColumn(name = "supply_id")
    private Supply supply;

    @ManyToOne
    @JoinColumn(name = "family_id")
    private Family family;

    private Integer quantity;

    public Distribution() {
    }

    public Distribution(Camp camp, Supply supply, Family family, Integer quantity) {
        this.camp = camp;
        this.supply = supply;
        this.family = family;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Camp getCamp() {
        return camp;
    }

    public void setCamp(Camp camp) {
        this.camp = camp;
    }

    public Supply getSupply() {
        return supply;
    }

    public void setSupply(Supply supply) {
        this.supply = supply;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}