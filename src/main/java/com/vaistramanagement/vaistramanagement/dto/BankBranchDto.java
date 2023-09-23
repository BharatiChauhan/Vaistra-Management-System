package com.vaistramanagement.vaistramanagement.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankBranchDto
{

    private Integer Id;

    private Integer bankId;
    private Integer stateId;
    private Integer districtId;




    @Pattern(regexp = "^[a-zA-Z ]{3,}$", message = "Branch name name must contain only alphabets with at least 3 characters!")
    private String branchName;


    private String branchCode;


    private String branchAdd;


    private String ifsc;


//    @Max(value = 10,message = "Phone no must be a positive Integer")
    private Integer phoneNo;


    private LocalTime fromTime;


    private LocalTime toTime;


    private String branchMicrr;


    private boolean status;


//    @JoinColumn(name = "bank_name")
//    private String bankName;
//
//
//
//    @JoinColumn(name="state_name")
//    private String stateName;
//
//
//
//    @JoinColumn(name="district_name")
//    private String districtName;



}
