package com.tema3.app.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.tema3.app.domain.enumeration.FuelType;

/**
 * A Car.
 */
@Entity
@Table(name = "car")
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "license_plate", nullable = false)
    private String licensePlate;

    @NotNull
    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "model")
    private String model;

    @NotNull
    @Column(name = "production_year", nullable = false)
    private Integer productionYear;

    @NotNull
    @Column(name = "engine_size", nullable = false)
    private Integer engineSize;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel")
    private FuelType fuel;

    @Column(name = "engine_power")
    private Integer enginePower;

    @Column(name = "engine_torque")
    private Integer engineTorque;

    @Column(name = "trunk_size")
    private Integer trunkSize;

    @Column(name = "price")
    private Integer price;

    @ManyToOne
    @JsonIgnoreProperties("cars")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public Car licensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
        return this;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public Car brand(String brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public Car model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public Car productionYear(Integer productionYear) {
        this.productionYear = productionYear;
        return this;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    public Integer getEngineSize() {
        return engineSize;
    }

    public Car engineSize(Integer engineSize) {
        this.engineSize = engineSize;
        return this;
    }

    public void setEngineSize(Integer engineSize) {
        this.engineSize = engineSize;
    }

    public FuelType getFuel() {
        return fuel;
    }

    public Car fuel(FuelType fuel) {
        this.fuel = fuel;
        return this;
    }

    public void setFuel(FuelType fuel) {
        this.fuel = fuel;
    }

    public Integer getEnginePower() {
        return enginePower;
    }

    public Car enginePower(Integer enginePower) {
        this.enginePower = enginePower;
        return this;
    }

    public void setEnginePower(Integer enginePower) {
        this.enginePower = enginePower;
    }

    public Integer getEngineTorque() {
        return engineTorque;
    }

    public Car engineTorque(Integer engineTorque) {
        this.engineTorque = engineTorque;
        return this;
    }

    public void setEngineTorque(Integer engineTorque) {
        this.engineTorque = engineTorque;
    }

    public Integer getTrunkSize() {
        return trunkSize;
    }

    public Car trunkSize(Integer trunkSize) {
        this.trunkSize = trunkSize;
        return this;
    }

    public void setTrunkSize(Integer trunkSize) {
        this.trunkSize = trunkSize;
    }

    public Integer getPrice() {
        return price;
    }

    public Car price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public Car user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Car)) {
            return false;
        }
        return id != null && id.equals(((Car) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Car{" +
            "id=" + getId() +
            ", licensePlate='" + getLicensePlate() + "'" +
            ", brand='" + getBrand() + "'" +
            ", model='" + getModel() + "'" +
            ", productionYear=" + getProductionYear() +
            ", engineSize=" + getEngineSize() +
            ", fuel='" + getFuel() + "'" +
            ", enginePower=" + getEnginePower() +
            ", engineTorque=" + getEngineTorque() +
            ", trunkSize=" + getTrunkSize() +
            ", price=" + getPrice() +
            "}";
    }
}
