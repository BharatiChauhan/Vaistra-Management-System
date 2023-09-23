package com.vaistramanagement.vaistramanagement.service;

import com.vaistramanagement.vaistramanagement.dto.BankDto;
import com.vaistramanagement.vaistramanagement.dto.CountryDto;
import com.vaistramanagement.vaistramanagement.dto.HttpResponse;
import com.vaistramanagement.vaistramanagement.entity.Bank;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BankService
{
    BankDto addBank(BankDto dto);



    BankDto getBankById(int Id);

//    List<BankDto> getAllBank(int pageNumber, int pageSize, String sortBy, String sortDirection);


    BankDto updateBank(BankDto dto, int Id);

    String deleteBankById(int Id);

    HttpResponse searchBank(String keyword, int pageNumber, int pageSize, String sortBy, String sortDirection);
    HttpResponse getAllBank(int pageNumber, int pageSize, String sortBy, String sortDirection);

    void uploadCsv(MultipartFile file) throws IOException;


    Bank create(Bank entity);
}
