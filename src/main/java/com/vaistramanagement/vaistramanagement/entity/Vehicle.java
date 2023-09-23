package com.vaistramanagement.vaistramanagement.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vehicle")
public class Vehicle
{

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "vehicle_id")
    private int Id;


    @Column(name = "vehicle_name")
    private String vehicleName;

}
