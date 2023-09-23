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
@Table(name = "equipment")
public class Equipment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "equipment_id")
    private int Id;


    @Column(name = "equipment_name")
    private String equipmentName;
}
