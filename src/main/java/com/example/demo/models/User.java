package com.example.demo.models;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {


  @Id
  @Column(name = "id")
  private long id;

  private String first_name;

  private String last_name;

  private String email;

  private String gender;

  public User(long id, String first_name, String last_name, String email, String gender){
    this.id = id;
    this.first_name = first_name;
    this.last_name = last_name;
    this.email = email;
    this.gender = gender;
  }

  public long getId(){
    return id;
  }

  public void setId(long id){
    this.id = id;
  }

  public String getFirst_name(){
    return first_name;
  }

  public void setFirst_name(String first_name){
    this.first_name = first_name;
  }

  public String getLast_name(){
    return last_name;
  }

  public void setLast_name(String last_name){
    this.last_name = last_name;
  }

  public String getEmail(){
    return email;
  }

  public void setEmail(String email){
    this.email = email;
  }

  public String getGender(){
    return gender;
  }

  public void setGender(String gender){
    this.gender = gender;
  }

  @Override
  public String toString(){
    return "User{" +
            "id=" + id +
            ", first_name='" + first_name + '\'' +
            ", last_name='" + last_name + '\'' +
            ", email='" + email + '\'' +
            ", gender='" + gender + '\'' +
            '}';
  }
}
