package com.epc.beans;

import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name = "part_details")
public class PartDetail {
    @Id
    @Column(name= "part_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long part_id;
    @Column(name= "part_name")
    private String part_name;
    @OneToOne
    @PrimaryKeyJoinColumn
    private Part part;

    public PartDetail() {
    }

    public long getPart_id() {
        return part_id;
    }

    public void setPart_id(long part_id) {
        this.part_id = part_id;
    }

    public String getPart_name() {
        return part_name;
    }

    public void setPart_name(String part_name) {
        this.part_name = part_name;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PartDetail that = (PartDetail) o;

        if (part_id != that.part_id) return false;
        if (part != null ? !part.equals(that.part) : that.part != null) return false;
        if (part_name != null ? !part_name.equals(that.part_name) : that.part_name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (part_id ^ (part_id >>> 32));
        result = 31 * result + (part_name != null ? part_name.hashCode() : 0);
        result = 31 * result + (part != null ? part.hashCode() : 0);
        return result;
    }
}
