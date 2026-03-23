package com.tadiwanashe.authservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false )
    private String username;

    public User() {}
   public String getUsername(){
        return this.username;
   }
   public void setUsername(String username){
        this.username=username;
   }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}