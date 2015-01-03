package com.epc.beans;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 *
 */
@Entity
@Table(name = "cars")
public class Car implements Serializable{

    private static final long serialVersionUID = 2L;
    @Id
    @Column(name= "car_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int car_id;
    @Column(name= "car_name")
    private String car_name;
    @ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(name = "parts_cars",
        joinColumns = {@JoinColumn(name="car_id")},
        inverseJoinColumns = {@JoinColumn(name = "part_id")})
    private List<Part> parts = new ArrayList<Part>();

    public Car() {
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;

        Car car = (Car) o;

        if (car_id != car.car_id) return false;
        if (!car_name.equals(car.car_name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = car_id;
        result = 31 * result + car_name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Car{" +
                "car_id=" + car_id +
                ", car_name='" + car_name + '\'' +
                '}';
    }
}
