package com.vaistramanagement.vaistramanagement.dto;


import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentDto
{
    private int Id;


    @Pattern(regexp = "^[a-zA-Z ]{3,}$", message = " Name must contain only alphabets with at least 3 characters")
    private String equipmentName;

}
