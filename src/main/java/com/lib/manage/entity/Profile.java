package com.lib.manage.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profile")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String birthday;

    private String avatar;

    private String email;

    private String address;

    private String phone;

    @OneToOne
    private User user;

    public Profile(String avatar, String phone, User user) {
        this.avatar = avatar;
        this.phone = phone;
        this.user = user;
    }
}
