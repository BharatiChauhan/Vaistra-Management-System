package com.vaistramanagement.vaistramanagement.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VillageDto {



    private Integer villageId;

    @Pattern(regexp = "^[a-zA-Z ]{3,}$", message = "Village name must contain only alphabets with at least 3 characters!")
    private String villageName;


    @Min(value = 1, message = "Sub-District ID must be a positive integer!")
    private Integer subDistrictId;

    @Min(value = 1, message = "District ID must be a positive integer!")
    private Integer districtId;

    @Min(value = 1, message = "State ID must be a positive integer!")
    private Integer stateId;

    @Min(value = 1, message = "Country ID must be a positive integer!")

    private Integer countryId;

    private boolean status;






//    @JoinColumn(name = "subDistrict_name")
////    private String subDistrictName;
}
