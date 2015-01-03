package com.epc.beans;

import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int user_id;
    @Column(name = "user_name")
    private String user_name;
    @Column(name = "user_pass")
    private String user_pass;

    public User() {
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

//        if (user_id != user.user_id) return false;
        if (user_name != null ? !user_name.equals(user.user_name) : user.user_name != null) return false;
        if (user_pass != null ? !user_pass.equals(user.user_pass) : user.user_pass != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = user_id;
        result = 31 * result + (user_name != null ? user_name.hashCode() : 0);
        result = 31 * result + (user_pass != null ? user_pass.hashCode() : 0);
        return result;
    }
}
