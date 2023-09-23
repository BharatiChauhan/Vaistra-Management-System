package com.vaistramanagement.vaistramanagement.controller;


import com.vaistramanagement.vaistramanagement.dto.HttpResponse;
import com.vaistramanagement.vaistramanagement.dto.SubDistrictDto;
import com.vaistramanagement.vaistramanagement.dto.VillageDto;
import com.vaistramanagement.vaistramanagement.service.VillageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("village")
public class VillageController {
    private final VillageService villageService;

    public VillageController(VillageService villageService) {
        this.villageService = villageService;
    }


    @PostMapping
    public ResponseEntity<VillageDto> addVillage(@Valid @RequestBody VillageDto villageDto) {
        return new ResponseEntity<>(villageService.addVillage(villageDto), HttpStatus.CREATED);
    }


    @GetMapping("all")
    public ResponseEntity<HttpResponse> getAllVillage(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                          @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                          @RequestParam(value = "sortBy", defaultValue = "villageId", required = false) String sortBy,
                                                          @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection) {
        return new ResponseEntity<>(villageService.getAllVillage(pageNumber, pageSize, sortBy, sortDirection), HttpStatus.FOUND);
    }

    @GetMapping("{villageId}")
    public ResponseEntity<VillageDto> getVillageById(@PathVariable int villageId) {
        return new ResponseEntity<>(villageService.getVillageById(villageId), HttpStatus.FOUND);
    }

    @PutMapping("{villageId}")
    public ResponseEntity<VillageDto> updateVillage(@Valid @RequestBody VillageDto villageDto, @PathVariable int villageId)
    {
        return new ResponseEntity<>(villageService.updateVillage(villageDto, villageId), HttpStatus.OK);
    }


    @PostMapping("/upload")
    public String uploadCsvFile(@RequestParam("file") MultipartFile file) throws IOException {
        villageService.uploadCsv(file);
        return "CSV file uploaded and data inserted into the database.";
    }
    //
//
//
    @DeleteMapping("{villageId}")
    public ResponseEntity<String> hardDeleteVillageById(@PathVariable int villageId)
    {
        return new ResponseEntity<>(villageService.deleteVillageById(villageId), HttpStatus.OK);
    }



    @GetMapping("search")
    public ResponseEntity<HttpResponse> searchByKeyword(@RequestParam(value = "keyword", defaultValue = "", required = false) String keyword,
                                                        @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                        @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                        @RequestParam(value = "sortBy", defaultValue = "subDistrictId", required = false) String sortBy,
                                                        @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection)
    {
        return new ResponseEntity<>(villageService.searchVillage(keyword, pageNumber, pageSize, sortBy, sortDirection), HttpStatus.OK);
    }
}