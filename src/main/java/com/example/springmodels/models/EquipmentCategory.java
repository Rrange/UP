package com.example.springmodels.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
public class EquipmentCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Category name cannot be empty")
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<SportEquipment> sportEquipments;

    public EquipmentCategory() {}

    public EquipmentCategory(String name) {
        this.name = name;
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

    public Set<SportEquipment> getSportEquipments() {
        return sportEquipments;
    }

    public void setSportEquipments(Set<SportEquipment> sportEquipments) {
        this.sportEquipments = sportEquipments;
    }
}
