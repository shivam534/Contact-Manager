package com.SMS.Smart_Contact_Manager.Entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String password;
    @Column(unique = true)
    private String email;


    private String role;



    private boolean enabled = true;
    private String imaageUrl;
    @Column(length = 300)
    private String about;

    private boolean AccountNonLocked = true;

    @Setter
    @Getter
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Contact> contacts = new ArrayList<>();

}
