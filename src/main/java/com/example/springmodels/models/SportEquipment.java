package com.example.springmodels.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import com.example.springmodels.models.EquipmentCategory;


@Entity
public class SportEquipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private EquipmentCategory category;

    @Column(nullable = false)
    private BigDecimal pricePerDay;

    @Column(nullable = false)
    private String condition;

    @Column(nullable = false)
    private boolean available;

    @OneToMany(mappedBy = "sportEquipment", cascade = CascadeType.ALL)
    private List<RentalOrder> rentalOrders;

    @OneToMany(mappedBy = "sportEquipment", cascade = CascadeType.ALL)
    private List<RepairLog> repairLogs;

    @OneToMany(mappedBy = "sportEquipment", cascade = CascadeType.ALL)
    private List<Feedback> feedbacks;

    // Getters and setters

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

    public EquipmentCategory getCategory() {
        return category;
    }

    public void setCategory(EquipmentCategory category) {
        this.category = category;
    }

    public BigDecimal getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(BigDecimal pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<RentalOrder> getRentalOrders() {
        return rentalOrders;
    }

    public void setRentalOrders(List<RentalOrder> rentalOrders) {
        this.rentalOrders = rentalOrders;
    }

    public List<RepairLog> getRepairLogs() {
        return repairLogs;
    }

    public void setRepairLogs(List<RepairLog> repairLogs) {
        this.repairLogs = repairLogs;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }
}
