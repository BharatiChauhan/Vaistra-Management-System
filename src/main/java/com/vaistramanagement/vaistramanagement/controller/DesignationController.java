package com.vaistramanagement.vaistramanagement.controller;


import com.vaistramanagement.vaistramanagement.dto.DesignationDto;
import com.vaistramanagement.vaistramanagement.dto.EntityDto;
import com.vaistramanagement.vaistramanagement.dto.HttpResponse;
import com.vaistramanagement.vaistramanagement.service.DesignationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("designation")
public class DesignationController
{
    private final DesignationService designationService;

    @Autowired
    public DesignationController(DesignationService designationService) {
        this.designationService = designationService;
    }

    @PostMapping
    public ResponseEntity<DesignationDto> addDesignation(@Valid @RequestBody DesignationDto designationDto) {
        return new ResponseEntity<>(designationService.addDesignation(designationDto), HttpStatus.CREATED);
    }

    @GetMapping("all")
    public ResponseEntity<List<DesignationDto>> getAllDesignation(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                        @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                        @RequestParam(value = "sortBy", defaultValue = "Id", required = false) String sortBy,
                                                        @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection) {

        return new ResponseEntity<>(designationService.getAllDesignation(pageNumber, pageSize, sortBy, sortDirection), HttpStatus.OK);
    }

    @GetMapping("{Id}")
    public ResponseEntity<DesignationDto> getDesignationById(@PathVariable int Id)
    {
        return new ResponseEntity<>(designationService.getDesignationById(Id), HttpStatus.FOUND);
    }


    @PutMapping("{Id}")
    public ResponseEntity<DesignationDto> updateDesignation(@RequestBody DesignationDto designationDto  , @PathVariable int Id)
    {
        return new ResponseEntity<>(designationService.updateDesignation(designationDto, Id), HttpStatus.OK);
    }
    //
//
//
    @DeleteMapping("{Id}")
    public ResponseEntity<String> hardDeleteDesignationById(@PathVariable int Id)
    {
        return new ResponseEntity<>(designationService.deleteDesignationById(Id), HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<HttpResponse> searchByKeyword(@RequestParam(value = "keyword", defaultValue = "", required = false) String keyword,
                                                        @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                        @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                        @RequestParam(value = "sortBy", defaultValue = "Id", required = false) String sortBy,
                                                        @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection)
    {
        return new ResponseEntity<>(designationService.searchDesignation(keyword, pageNumber, pageSize, sortBy, sortDirection), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public String uploadCsvFile(@RequestParam("file") MultipartFile file) throws IOException {
        designationService.uploadCsv(file);
        return "CSV file uploaded and data inserted into the database.";
    }
}
