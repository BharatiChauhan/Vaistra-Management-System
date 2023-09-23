package com.vaistramanagement.vaistramanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubDistrict {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subdistrict_id")
    private Integer subDistrictId;

    @Column(name = "subDistrict_name")
    private String subDistrictName;

    @Column(name = "status")
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToOne
    @JoinColumn(name="country_id")
    private Country country;



    @OneToMany(mappedBy = "subDistrict", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Village> villages = new ArrayList<>();


}
