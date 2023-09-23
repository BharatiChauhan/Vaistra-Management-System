package com.vaistramanagement.vaistramanagement.serviceimpl;

import com.vaistramanagement.vaistramanagement.dto.BankDto;
import com.vaistramanagement.vaistramanagement.dto.HttpResponse;
import com.vaistramanagement.vaistramanagement.entity.Bank;
import com.vaistramanagement.vaistramanagement.entity.Country;
import com.vaistramanagement.vaistramanagement.exception.DuplicateEntryException;
import com.vaistramanagement.vaistramanagement.exception.ResourceNotFoundException;
import com.vaistramanagement.vaistramanagement.repositories.BankRepository;
import com.vaistramanagement.vaistramanagement.service.BankService;
import com.vaistramanagement.vaistramanagement.utils.AppUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class BankServiceImpl implements BankService {
    private final BankRepository bankRepository;
    private final AppUtils appUtils;

    public BankServiceImpl(BankRepository bankRepository, AppUtils appUtils) {
        this.bankRepository = bankRepository;
        this.appUtils = appUtils;
    }

    @Override
    public BankDto addBank(BankDto dto) {

        dto.setBankName(dto.getBankName().toUpperCase().trim());

        // HANDLE DUPLICATE NAME ENTRY EXCEPTION
        if (bankRepository.existsByBankName(dto.getBankName()))
            throw new DuplicateEntryException("Bank with name '" + dto.getBankName() + "' already exist!");

//        Country country = new Country();
        Bank bank = new Bank();
        bank.setBankName(dto.getBankName());
        bank.setShortName(dto.getShortName());
//        bank.setLogo(dto.getLogo());
        bank.setStatus(true);

        return appUtils.bankToDto(bankRepository.save(bank));
    }


    @Override
    public BankDto getBankById(int Id) {
        return appUtils.bankToDto(bankRepository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("Bank with id '" + Id + "' Not Found!")));
    }

    @Override
    public HttpResponse getAllBank(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Bank> pageBank = bankRepository.findAll(pageable);
//
        List<BankDto> dtos = appUtils.banksToDtos(pageBank.getContent());
        return HttpResponse.builder()
                .pageNumber(pageBank.getNumber())
                .pageSize(pageBank.getSize())
                .totalElements(pageBank.getTotalElements())
                .totalPages(pageBank.getTotalPages())
                .isLastPage(pageBank.isLast())
                .data(dtos)
                .build();
    }

    @Override
    public BankDto updateBank(BankDto dto, int Id) {
        Bank bank = bankRepository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("Bank with Id '" + Id + "' not found!"));

        Bank bnk = bankRepository.findByBankName(dto.getBankName());
//        if(bnk != null)
//            throw new DuplicateEntryException("Bank with name '"+bnk.getBankName()+"' already exist!");

        bank.setBankName(dto.getBankName().toUpperCase());
        bank.setShortName(dto.getShortName());


        return appUtils.bankToDto(bankRepository.save(bank));
    }

    @Override
    public String deleteBankById(int Id) {
        bankRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Bank with Id '" + Id + "' not found!"));

        bankRepository.deleteById(Id);
        return "Bank with Id '" + Id + "' deleted";
    }

    @Override
    public HttpResponse searchBank(String keyword, int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Bank> pageBank = bankRepository.findByBankNameContainingIgnoreCase(keyword, pageable);
        List<BankDto> dtos = appUtils.banksToDtos(pageBank.getContent());
        return HttpResponse.builder()
                .pageNumber(pageBank.getNumber())
                .pageSize(pageBank.getSize())
                .totalElements(pageBank.getTotalElements())
                .totalPages(pageBank.getTotalPages())
                .isLastPage(pageBank.isLast())
                .data(dtos)
                .build();
    }

    public void uploadCsv(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            List<Bank> dataList = reader.lines()
                    .skip(1) // Skip header row if needed
                    .map(line -> {
                        String[] parts = line.split(",");
                        Bank csvData = new Bank();

//                        csvData.setCountryId(Integer.parseInt(parts[0]));
                        csvData.setBankName(parts[1]);
                        csvData.setShortName(parts[2]);
                        csvData.setStatus(Boolean.parseBoolean(parts[3]));

                        // Set other columns as needed
                        return csvData;
                    })
                    .toList();
            bankRepository.saveAll(dataList);
        }
    }

    @Override
    public Bank create(Bank entity) {

        if (bankRepository.existsByBankName(entity.getBankName()))
            throw new DuplicateEntryException("Bank with name '" + entity.getBankName() + "' already exist!");
        entity.setBankName(entity.getBankName().toUpperCase().trim());
        entity.setShortName(entity.getShortName().toUpperCase().trim());
        return bankRepository.save(entity);
    }


}

