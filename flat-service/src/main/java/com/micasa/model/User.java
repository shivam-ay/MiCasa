package com.micasa.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User
{
    @Id
    @Column(name = "user_id")
    private String userId;
    private String name;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Flat> flatList;
}
