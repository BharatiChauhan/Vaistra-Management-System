package com.vaistramanagement.vaistramanagement.controller;


import com.vaistramanagement.vaistramanagement.dto.BankBranchDto;
import com.vaistramanagement.vaistramanagement.dto.BankDto;
import com.vaistramanagement.vaistramanagement.dto.HttpResponse;
import com.vaistramanagement.vaistramanagement.dto.StateDto;
import com.vaistramanagement.vaistramanagement.service.BankBranchService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("bankbranch")
public class BankBranchController
{
    private final BankBranchService bankBranchService;

    public BankBranchController(BankBranchService bankBranchService) {
        this.bankBranchService = bankBranchService;
    }

    @PostMapping
    public ResponseEntity<BankBranchDto> addBankBranch(@Valid @RequestBody BankBranchDto dto) {
        return new ResponseEntity<>(bankBranchService.addBankBranch(dto), HttpStatus.CREATED);
    }

    @GetMapping("all")
    public ResponseEntity<HttpResponse> getAllBankBranch(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                     @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                     @RequestParam(value = "sortBy", defaultValue = "Id", required = false) String sortBy,
                                                     @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection) {
        return new ResponseEntity<>(bankBranchService.getAllBankBranch(pageNumber, pageSize, sortBy, sortDirection), HttpStatus.FOUND);
    }

    @GetMapping("search")
    public ResponseEntity<HttpResponse> searchByKeyword(@RequestParam(value = "keyword", defaultValue = "", required = false) String keyword,
                                                        @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                        @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                        @RequestParam(value = "sortBy", defaultValue = "Id", required = false) String sortBy,
                                                        @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection)
    {
        return new ResponseEntity<>(bankBranchService.searchBranch(keyword, pageNumber, pageSize, sortBy, sortDirection), HttpStatus.OK);
    }

    @GetMapping("{Id}")
    public ResponseEntity<BankBranchDto> getBankBranchById(@PathVariable int Id) {
        return new ResponseEntity<>(bankBranchService.getBankBranchById(Id), HttpStatus.OK);
    }

    @PutMapping("{Id}")
    public ResponseEntity<BankBranchDto> updateBankBranch(@RequestBody BankBranchDto dto, @PathVariable int Id) {
        return new ResponseEntity<>(bankBranchService.updateBankBranch(dto, Id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{Id}")
    public ResponseEntity<String> deleteBankBranchById(@PathVariable int Id) {
        return new ResponseEntity<>(bankBranchService.deleteBankBranchById(Id), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public String uploadCsvFile(@RequestParam("file") MultipartFile file) throws IOException {
        bankBranchService.uploadCsv(file);
        return "CSV file uploaded and data inserted into the database.";
    }
}
