package com.vaistramanagement.vaistramanagement.dto;

import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubDistrictDto {

    private Integer subDistrictId;

    @Pattern(regexp = "^[a-zA-Z ]{3,}$", message = "Sub-District name must contain only alphabets with at least 3 characters!")
    private String subDistrictName;

    private boolean status;

    @Min(value = 1, message = "District ID must be a positive integer!")
    private Integer districtId;

    @Min(value = 1, message = "State ID must be a positive integer!")
    private Integer stateId;

    @Min(value = 1, message = "Country ID must be a positive integer!")
    private Integer countryId;

    @JoinColumn(name = "district_name")
    private String districtName;



}
