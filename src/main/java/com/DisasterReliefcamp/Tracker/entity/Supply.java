package com.DisasterReliefcamp.Tracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name = "supplies")
public class Supply {
    @OneToMany(mappedBy = "supply", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Distribution> distributions;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Supply name is required")
    private String name;

    @NotBlank(message = "Supply type is required")
    private String type;

    @NotNull(message = "Supply quantity is required")
    @Positive(message = "Supply quantity must be greater than zero")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "camp_id", nullable = false)
    private Camp camp;

    public Supply() {
    }

    public Supply(String name, String type, Integer quantity, Camp camp) {
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.camp = camp;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Camp getCamp() {
        return camp;
    }

    public void setCamp(Camp camp) {
        this.camp = camp;
    }
}