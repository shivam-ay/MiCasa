package com.micasa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User
{
    @Id
    @Column(name = "user_id")
    private String userId;
    private String name;
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Flat> flatList;
}
