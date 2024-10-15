package com.SMS.Smart_Contact_Manager.Entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Contact {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long id;

private String firstname;
private String secondname;
private String email;
@Column(length = 10)
private String phone;
private String work;
private String image;
@Column(length = 200)
private String description;

@ManyToOne
@JsonBackReference
private User user;

}
