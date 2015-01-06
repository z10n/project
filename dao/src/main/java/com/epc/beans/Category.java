package com.epc.beans;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 *
 */
@Entity
@Table(name = "categories")
public class Category implements Serializable {

    private static final long serialVersionUID = 3L;
    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int category_id;
    @Column(name = "category_name")
    private String category_name;
    @OneToMany(mappedBy = "category")
    private List<Part> parts = new ArrayList<>();

    public Category() {
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
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
        if (!(o instanceof Category)) return false;

        Category category = (Category) o;

        if (category_id != category.category_id) return false;
        if (!category_name.equals(category.category_name)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = category_id;
        result = 31 * result + category_name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Category{" +
                "category_id=" + category_id +
                ", category_name='" + category_name + '\'' +
                '}';
    }
}
