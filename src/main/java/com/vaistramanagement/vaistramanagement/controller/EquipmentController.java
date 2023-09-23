package com.vaistramanagement.vaistramanagement.controller;


import com.vaistramanagement.vaistramanagement.dto.DesignationDto;
import com.vaistramanagement.vaistramanagement.dto.EquipmentDto;
import com.vaistramanagement.vaistramanagement.dto.HttpResponse;
import com.vaistramanagement.vaistramanagement.service.EquipmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("equipment")
public class EquipmentController
{
    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @PostMapping
    public ResponseEntity<EquipmentDto> addEquipment(@Valid @RequestBody EquipmentDto equipmentDto) {
        return new ResponseEntity<>(equipmentService.addEquipment(equipmentDto), HttpStatus.CREATED);
    }

    @GetMapping("all")
    public ResponseEntity<List<EquipmentDto>> getAllEquipment(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                                  @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                                  @RequestParam(value = "sortBy", defaultValue = "Id", required = false) String sortBy,
                                                                  @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection) {

        return new ResponseEntity<>(equipmentService.getAllEquipment(pageNumber, pageSize, sortBy, sortDirection), HttpStatus.OK);
    }

    @GetMapping("{Id}")
    public ResponseEntity<EquipmentDto> getEquipmentById(@PathVariable int Id)
    {
        return new ResponseEntity<>(equipmentService.getEquipmentById(Id), HttpStatus.FOUND);
    }


    @PutMapping("{Id}")
    public ResponseEntity<EquipmentDto> updateEquipment(@RequestBody EquipmentDto equipmentDto , @PathVariable int Id)
    {
        return new ResponseEntity<>(equipmentService.updateEquipment(equipmentDto, Id), HttpStatus.OK);
    }
    //
//
//
    @DeleteMapping("{Id}")
    public ResponseEntity<String> hardDeleteEquipmentById(@PathVariable int Id)
    {
        return new ResponseEntity<>(equipmentService.deleteEquipmentById(Id), HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<HttpResponse> searchByKeyword(@RequestParam(value = "keyword", defaultValue = "", required = false) String keyword,
                                                        @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                        @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                        @RequestParam(value = "sortBy", defaultValue = "Id", required = false) String sortBy,
                                                        @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection)
    {
        return new ResponseEntity<>(equipmentService.searchEquipment(keyword, pageNumber, pageSize, sortBy, sortDirection), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public String uploadCsvFile(@RequestParam("file") MultipartFile file) throws IOException {
        equipmentService.uploadCsv(file);
        return "CSV file uploaded and data inserted into the database.";
    }
}
