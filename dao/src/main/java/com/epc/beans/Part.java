package com.epc.beans;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 *
 */
@Entity
@Table(name = "parts")
public class Part implements Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name= "part_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int part_id;
    @OneToOne(mappedBy = "part", cascade = CascadeType.ALL)
    private PartDetail partDetail;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToMany(mappedBy = "parts")
    private List<Car> cars = new ArrayList<Car>();

    public Part() {
    }

    public int getPart_id() {
        return part_id;
    }

    public void setPart_id(int part_id) {
        this.part_id = part_id;
    }

    public PartDetail getPartDetail() {
        return partDetail;
    }

    public void setPartDetail(PartDetail partDetail) {
        this.partDetail = partDetail;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Part)) return false;

        Part part = (Part) o;

        if (part_id != part.part_id) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = (part_id ^ (part_id >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Part{" +
                "part_id=" + part_id +
                '}';
    }
}
