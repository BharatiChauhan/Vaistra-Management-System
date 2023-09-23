package com.vaistramanagement.vaistramanagement.service;

import com.vaistramanagement.vaistramanagement.dto.BankBranchDto;
import com.vaistramanagement.vaistramanagement.dto.BankDto;
import com.vaistramanagement.vaistramanagement.dto.HttpResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BankBranchService
{

    BankBranchDto addBankBranch(BankBranchDto dto);



    BankBranchDto getBankBranchById(int Id);

//    List<BankDto> getAllBankBranch(int pageNumber, int pageSize, String sortBy, String sortDirection);


    BankBranchDto updateBankBranch(BankBranchDto dto, int Id);

    String deleteBankBranchById(int Id);

    HttpResponse searchBranch(String keyword, int pageNumber, int pageSize, String sortBy, String sortDirection);
    HttpResponse getAllBankBranch(int pageNumber, int pageSize, String sortBy, String sortDirection);

    void uploadCsv(MultipartFile file) throws IOException;


}
