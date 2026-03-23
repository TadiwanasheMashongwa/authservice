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
    @Column(unique = true,nullable = false )
    private String email;
    @Column(nullable = false)
    private String password;

    public User() {}
   public String getUsername(){
        return this.username;
   }
   public void setEmail(String email){
        this.email=email;
   }
   public void setPassword(String password){
        this.password=password;
   }
   public String getEmail(){
        return this.email;
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