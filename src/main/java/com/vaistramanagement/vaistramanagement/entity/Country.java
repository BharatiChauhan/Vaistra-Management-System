package com.vaistramanagement.vaistramanagement.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "country")
@Builder

public class Country
{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "country_id")
    private int countryId;
    @Column(name = "country_name")
    private String countryName;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "country_states",
            joinColumns = @JoinColumn(name = "countryId"),
            inverseJoinColumns = @JoinColumn(name = "stateId")
    )
    List<State> states = new ArrayList<>();

    @Column(name = "status")
    private boolean status;


    public Country(String india, boolean b) {
    }
}
