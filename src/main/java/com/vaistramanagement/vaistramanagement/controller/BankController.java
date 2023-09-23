package com.vaistramanagement.vaistramanagement.controller;


import com.vaistramanagement.vaistramanagement.dto.BankDto;

import com.vaistramanagement.vaistramanagement.dto.HttpResponse;
import com.vaistramanagement.vaistramanagement.entity.Bank;
import com.vaistramanagement.vaistramanagement.exception.DuplicateEntryException;
import com.vaistramanagement.vaistramanagement.service.BankService;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("bank")
public class BankController {
    private final BankService bankService;

    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }


    @PostMapping
    public ResponseEntity<BankDto> addBank(@Valid @RequestBody  BankDto dto )  {


//      dto.setLogo(logo);
        return new ResponseEntity<>(bankService.addBank(dto), HttpStatus.CREATED);
    }


    @GetMapping("all")
    public ResponseEntity <HttpResponse> getAllBank(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                            @RequestParam(value = "sortBy", defaultValue = "Id", required = false) String sortBy,
                                                            @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection) {

        return new ResponseEntity<>(bankService.getAllBank(pageNumber, pageSize, sortBy, sortDirection), HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<HttpResponse> searchByKeyword(@RequestParam(value = "keyword", defaultValue = "", required = false) String keyword,
                                                        @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                        @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                        @RequestParam(value = "sortBy", defaultValue = "Id", required = false) String sortBy,
                                                        @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection)
    {
        return new ResponseEntity<>(bankService.searchBank(keyword, pageNumber, pageSize, sortBy, sortDirection), HttpStatus.OK);
    }

    @GetMapping("{Id}")
    public ResponseEntity<BankDto> getBankById(@PathVariable int Id) {
        return new ResponseEntity<>(bankService.getBankById(Id), HttpStatus.OK);
    }

    @PutMapping("{Id}")
    public ResponseEntity<BankDto> updateBank(@RequestBody BankDto dto, @PathVariable int Id) {
        return new ResponseEntity<>(bankService.updateBank(dto, Id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{Id}")
    public ResponseEntity<String> deleteBankById(@PathVariable int Id) {
        return new ResponseEntity<>(bankService.deleteBankById(Id), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public String uploadCsvFile(@RequestParam("file") MultipartFile file) throws IOException {
        bankService.uploadCsv(file);
        return "CSV file uploaded and data inserted into the database.";
    }

    @PostMapping("/create")
    public ResponseEntity<Bank> createBank(@RequestParam("name") String bankName,@RequestParam("shortname") String shortname, @RequestParam("file") MultipartFile file) {
        try {


            Bank entity = new Bank();
            entity.setBankName(bankName);
            entity.setShortName(shortname);
            entity.setStatus(true);

            // Check if a file is provided
            if (!file.isEmpty()) {
                // You can store the image data as a byte array
                entity.setLogo(file.getBytes());
            }

            Bank createdEntity = bankService.create(entity);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEntity);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}


