package com.micasa.societyservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "societies")
public class Society
{
    @Id
    @Column(name = "society_id")
    @UuidGenerator
    private String societyId;
    @Column(name = "society_name")
    private String societyName;
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "society",cascade = CascadeType.ALL)
    private List<Flat> flatList;
}
