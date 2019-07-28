package com.example.demo1.model;

import lombok.Data;

@Data
public class User {

    private Integer id;


    private String accountId;


    private String name;


    private String token;


    private Long gmtCreate;


    private Long gmtModified;


    private String bio;


    private String avatarUrl;

}