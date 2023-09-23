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
public class StateDto {
    private Integer stateId;

    @Pattern(regexp = "^[a-zA-Z ]{3,}$", message = "Country name must contain only alphabets with at least 3 characters!")
    private String stateName;

    private boolean status;

    @Min(value = 1, message = "Country ID must be a positive integer!")
    private Integer countryId;
    @JoinColumn(name = "country_name")
    private String countryName;

}
