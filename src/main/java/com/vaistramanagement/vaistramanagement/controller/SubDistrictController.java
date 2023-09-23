package com.vaistramanagement.vaistramanagement.controller;

import com.vaistramanagement.vaistramanagement.dto.DistrictDto;
import com.vaistramanagement.vaistramanagement.dto.HttpResponse;
import com.vaistramanagement.vaistramanagement.dto.SubDistrictDto;
import com.vaistramanagement.vaistramanagement.service.DistrictService;
import com.vaistramanagement.vaistramanagement.service.SubDistrictService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("subdistrict")
public class SubDistrictController {

    private final SubDistrictService subdistrictService;

    @Autowired
    public SubDistrictController(SubDistrictService subdistrictService) {
        this.subdistrictService = subdistrictService;
    }


    @PostMapping
    public ResponseEntity<SubDistrictDto> addDistrict(@Valid @RequestBody SubDistrictDto subdistrictDto) {
        return new ResponseEntity<>(subdistrictService.addSubDistrict(subdistrictDto), HttpStatus.CREATED);
    }


    @GetMapping("{subdistrictId}")
    public ResponseEntity<SubDistrictDto> getSubDistrictById(@PathVariable int subdistrictId)
    {
        return new ResponseEntity<>(subdistrictService.getSubDistrictById(subdistrictId), HttpStatus.FOUND);
    }


    @GetMapping("all")
    public ResponseEntity<List<SubDistrictDto>> getAllSubDistricts(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                             @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                             @RequestParam(value = "sortBy", defaultValue = "subDistrictId", required = false) String sortBy,
                                                             @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection)
    {
        return new ResponseEntity<>(subdistrictService.getAllSubDistricts(pageNumber, pageSize, sortBy, sortDirection), HttpStatus.FOUND);
    }
//
//
//    @GetMapping
//    public ResponseEntity<List<SubDistrictDto>> getAllSubDistrictsByActive(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
//                                                                     @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
//                                                                     @RequestParam(value = "sortBy", defaultValue = "subDistrictId", required = false) String sortBy,
//                                                                     @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection)
//    {
//        return new ResponseEntity<>(subdistrictService.getAllSubDistrictsByActiveState(pageNumber, pageSize, sortBy, sortDirection), HttpStatus.FOUND);
//    }

//
//    @GetMapping("districtId/{districtId}")
//    public ResponseEntity<List<SubDistrictDto>> getSubDistrictByDistrictId(@PathVariable int districtId)
//    {
//        return new ResponseEntity<>(subdistrictService.getSubDistrictsByDistrictId(districtId), HttpStatus.FOUND);
//    }
//
////
////    @GetMapping("countryId/{countryId}")
////    public ResponseEntity<List<SubDistrictDto>> getSubDistrictByCountryId(@PathVariable int countryId)
////    {
////        return new ResponseEntity<>(subdistrictService.getSubDistrictsByCountryId(countryId), HttpStatus.FOUND);
////    }
//
//
    @PutMapping("{subdistrictId}")
    public ResponseEntity<SubDistrictDto> updateSubDistrict(@Valid @RequestBody SubDistrictDto subdistrictDto, @PathVariable int subdistrictId)
    {
        return new ResponseEntity<>(subdistrictService.updateSubDistrict(subdistrictDto, subdistrictId), HttpStatus.OK);
    }
//
//
//
    @DeleteMapping("{subdistrictId}")
    public ResponseEntity<String> hardDeleteSubDistrictById(@PathVariable int subdistrictId)
    {
        return new ResponseEntity<>(subdistrictService.deleteSubDistrictById(subdistrictId), HttpStatus.OK);
    }



    @GetMapping("search")
    public ResponseEntity<HttpResponse> searchByKeyword(@RequestParam(value = "keyword", defaultValue = "", required = false) String keyword,
                                                        @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                        @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                        @RequestParam(value = "sortBy", defaultValue = "subDistrictId", required = false) String sortBy,
                                                        @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection)
    {
        return new ResponseEntity<>(subdistrictService.searchSubDistrict(keyword, pageNumber, pageSize, sortBy, sortDirection), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public String uploadCsvFile(@RequestParam("file") MultipartFile file) throws IOException {
        subdistrictService.uploadCsv(file);
        return "CSV file uploaded and data inserted into the database.";
    }
}

