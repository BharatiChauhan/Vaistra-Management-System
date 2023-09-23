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
@Table(name = "mineral")
public class Mineral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "mineral_id")
    private int mineralId;
    @Column(name = "category")
    private String mineralCategory;

    @Column(name = "mineral_name")
    private String mineralName;

    @Column(name = "atr_name")
    private String atrName;

    @Column(name="hsn_code")
    private Integer hsnCode;
    @Column(name = "mineral_grade")
    private String grade;


}
