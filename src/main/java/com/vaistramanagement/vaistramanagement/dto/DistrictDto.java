package com.vaistramanagement.vaistramanagement.dto;

import com.vaistramanagement.vaistramanagement.entity.Country;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DistrictDto {

    private Integer districtId;

    @Pattern(regexp = "^[a-zA-Z ]{3,}$", message = "District name must contain only alphabets with at least 3 characters!")
    private String districtName;

    private boolean status;

    @Min(value = 1, message = "State ID must be a positive integer!")
    private Integer stateId;
    @Min(value = 1, message = "Country ID must be a positive integer!")
    private Integer countryId;


    @JoinColumn(name = "state_name")
    private String stateName;













}
