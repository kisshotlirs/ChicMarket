package com.mojo.oauth2.model.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author mojo
 * @description: 用户实体类
 * @date 2023/7/19 0019 21:49
 */
@Data
@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "passwd")
    private String password;

    @Column(name = "user_role")
    private String userRole;
}
