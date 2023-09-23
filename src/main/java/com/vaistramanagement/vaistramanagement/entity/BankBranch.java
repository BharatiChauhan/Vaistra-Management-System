package com.vaistramanagement.vaistramanagement.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bank_branch")

public class BankBranch
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_id")
    private int Id;


    @ManyToOne
    @JoinColumn(name="bank_id")
    private Bank bank;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToOne
    @JoinColumn(name="district_id")
    private District district;

    @Column(name="branch_name")
    private String branchName;

    @Column(name="branch_code")
    private String branchCode;

    @Column(name="branch_address")
    private String branchAdd;

    @Column(name="branch_IFSC")
    private String ifsc;

    @Column(name="phone_no")
    private Integer phoneNo;

    @Column(name="branch_from_time")
    private LocalTime fromTime;

    @Column(name="branch_to_time")
    private LocalTime toTime;

    @Column(name="branch_MICRR")
    private String branchMicrr;

    @Column(name = "status")
    private boolean status;


}
