package com.vaistramanagement.vaistramanagement.serviceimpl;

import com.vaistramanagement.vaistramanagement.dto.BankBranchDto;
import com.vaistramanagement.vaistramanagement.dto.HttpResponse;
import com.vaistramanagement.vaistramanagement.dto.StateDto;
import com.vaistramanagement.vaistramanagement.entity.*;
import com.vaistramanagement.vaistramanagement.exception.DuplicateEntryException;
import com.vaistramanagement.vaistramanagement.exception.ResourceNotFoundException;
import com.vaistramanagement.vaistramanagement.repositories.BankBranchRepository;
import com.vaistramanagement.vaistramanagement.repositories.BankRepository;
import com.vaistramanagement.vaistramanagement.repositories.DistrictRepository;
import com.vaistramanagement.vaistramanagement.repositories.StateRepository;
import com.vaistramanagement.vaistramanagement.service.BankBranchService;
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
import java.time.LocalTime;
import java.util.List;

@Service
public class BankBranchServiceImpl implements BankBranchService
{
    private final BankBranchRepository bankBranchRepository;
    private final BankRepository bankRepository;
    private final StateRepository stateRepository;
    private final DistrictRepository districtRepository;
    private final AppUtils appUtils;

    public BankBranchServiceImpl(BankBranchRepository bankBranchRepository, BankRepository bankRepository, StateRepository stateRepository, DistrictRepository districtRepository, AppUtils appUtils) {
        this.bankBranchRepository = bankBranchRepository;
        this.bankRepository = bankRepository;
        this.stateRepository = stateRepository;
        this.districtRepository = districtRepository;
        this.appUtils = appUtils;
    }


    @Override
    public BankBranchDto addBankBranch(BankBranchDto dto) {
        dto.setBranchName(dto.getBranchName().toUpperCase().trim());

        // HANDLE DUPLICATE NAME ENTRY EXCEPTION
        if (bankBranchRepository.existsByBranchName(dto.getBranchName()))
            throw new DuplicateEntryException("Branch with name '" + dto.getBranchName() + "' already exist!");
        BankBranch bankbranch = new BankBranch();
        Bank bank = bankRepository.findById(dto.getBankId())
                .orElseThrow(()->new ResourceNotFoundException("Bank with ID '"+dto.getBankId()+"' not found!"));

        State state=stateRepository.findById(dto.getStateId())
                .orElseThrow(()->new ResourceNotFoundException("State ID '"+dto.getStateId()+"' not found!"));

        District district=districtRepository.findById(dto.getDistrictId())
                .orElseThrow(()->new ResourceNotFoundException("District with ID '"+dto.getDistrictId()+"' not found!"));


        bankbranch.setBank(bank);
        bankbranch.setState(state);
        bankbranch.setDistrict(district);
        bankbranch.setBranchName(dto.getBranchName());
        bankbranch.setBranchCode(dto.getBranchCode());
        bankbranch.setBranchAdd(dto.getBranchAdd());
        bankbranch.setIfsc(dto.getIfsc());
        bankbranch.setPhoneNo(dto.getPhoneNo());
        bankbranch.setBranchMicrr(dto.getBranchMicrr());
        bankbranch.setFromTime(dto.getFromTime());
        bankbranch.setToTime(dto.getToTime());
        bankbranch.setStatus(true);

        return appUtils.bankbranchToDto(bankBranchRepository.save(bankbranch));
    }

    @Override
    public BankBranchDto getBankBranchById(int Id) {
        return appUtils.bankbranchToDto(bankBranchRepository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("BankBranch with id '" + Id + "' Not Found!")));
    }

    @Override
    public BankBranchDto updateBankBranch(BankBranchDto dto, int Id) {
        BankBranch branch = bankBranchRepository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("BankBranch with Id '" + Id + "' not found!"));

        BankBranch bnk = bankBranchRepository.findByBranchName(dto.getBranchName());
//        if(bnk != null)
//            throw new DuplicateEntryException("Bank with name '"+bnk.getBankName()+"' already exist!");


        branch.setBranchName(dto.getBranchName());
        branch.setBranchMicrr(dto.getBranchMicrr());
       branch.setPhoneNo(dto.getPhoneNo());
       branch.setIfsc(dto.getIfsc());
       branch.setBranchAdd(dto.getBranchAdd());
       branch.setBranchCode(dto.getBranchCode());
       branch.setFromTime(dto.getFromTime());
       branch.setToTime(dto.getToTime());



        return appUtils.bankbranchToDto(bankBranchRepository.save(branch));
    }


    @Override
    public String deleteBankBranchById(int Id) {
        bankBranchRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("BankBranch with Id '" + Id + "' not found!"));

        bankBranchRepository.deleteById(Id);
        return "BankBranch with Id '" + Id + "' deleted";
    }

    @Override
    public HttpResponse searchBranch(String keyword, int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<BankBranch> pageBankBranch = bankBranchRepository.findByBranchNameContainingIgnoreCase(keyword,  pageable);

        List<BankBranchDto> dtos = appUtils.bankbranchesToDtos(pageBankBranch.getContent());

        return HttpResponse.builder()
                .pageNumber(pageBankBranch.getNumber())
                .pageSize(pageBankBranch.getSize())
                .totalElements(pageBankBranch.getTotalElements())
                .totalPages(pageBankBranch.getTotalPages())
                .isLastPage(pageBankBranch.isLast())
                .data(dtos)
                .build();
    }

    @Override
    public HttpResponse getAllBankBranch(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<BankBranch> pageBankBranch = bankBranchRepository.findAll(pageable);
        List<BankBranchDto> states = appUtils.bankbranchesToDtos(pageBankBranch.getContent());

        return HttpResponse.builder()
                .pageNumber(pageBankBranch.getNumber())
                .pageSize(pageBankBranch.getSize())
                .totalElements(pageBankBranch.getTotalElements())
                .totalPages(pageBankBranch.getTotalPages())
                .isLastPage(pageBankBranch.isLast())
                .data(states)
                .build();
    }



    @Override
    public void uploadCsv(MultipartFile file) throws IOException {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            List<BankBranch> dataList = reader.lines()
                    .skip(1) // Skip header row if needed
                    .map(line -> {
                        String[] parts = line.split(",");
                        BankBranch csvData = new BankBranch();

//                        csvData.setCountryId(Integer.parseInt(parts[0]));

                        csvData.setBranchAdd(parts[1]);
                        csvData.setBranchCode(parts[2]);
                        csvData.setBranchMicrr(parts[3]);
                        csvData.setBranchName(parts[4]);
                        csvData.setFromTime(LocalTime.parse(parts[5]));
                        csvData.setIfsc(parts[6]);
                        csvData.setPhoneNo(Integer.valueOf(parts[7]));


                        csvData.setStatus(Boolean.parseBoolean(parts[8]));

                        // Set other columns as needed
                        return csvData;
                    })
                    .toList();
            bankBranchRepository.saveAll(dataList);
        }

    }
}
