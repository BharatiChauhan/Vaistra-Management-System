package com.vaistramanagement.vaistramanagement.dto;

import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountryDto {

    private Integer countryId;

    @Pattern(regexp = "^[a-zA-Z ]{3,}$", message = "Country name must contain only alphabets with at least 3 characters")
    private String countryName;

    private boolean status;

}
