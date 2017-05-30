package ru.stqa.pft.model;


import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.persistence.Table;


/**
 * Created by yuos1116 on 5/30/2017.
 */

@Entity
@Table(name = "mantis_user_table")
public class Users {

    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;


    public Users withEmail(String email) {
        this.email = email;
        return this;
    }
    public Users withUsername(String username) {
        this.username = username;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }





}
