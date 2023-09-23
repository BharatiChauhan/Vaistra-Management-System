package com.vaistramanagement.vaistramanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Village {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "village_id")
    private Integer villageId;

    @Column(name = "village_name")
    private String villageName;

    @Column(name = "status")
    private boolean status;

    @ManyToOne
//    @JoinColumn(name = "subDistrict_id")
    @JoinColumn(name = "subdistrict_id")
    private SubDistrict subDistrict;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToOne
    @JoinColumn(name="country_id")
    private Country country;

}

