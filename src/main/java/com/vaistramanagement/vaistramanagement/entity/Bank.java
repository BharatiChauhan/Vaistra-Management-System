package com.vaistramanagement.vaistramanagement.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bank")
public class Bank
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "bank_id")
    private int Id;
    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "bank_shortName")
    private String shortName;

    @Column(name="bank_logo",length =64)
    @Lob
    private byte[] logo;



//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "country_states",
//            joinColumns = @JoinColumn(name = "countryId"),
//            inverseJoinColumns = @JoinColumn(name = "stateId")
//    )
//    List<State> states = new ArrayList<>();

    @Column(name = "status")
    private boolean status;

}
