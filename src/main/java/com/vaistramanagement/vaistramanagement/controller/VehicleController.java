package com.vaistramanagement.vaistramanagement.controller;


import com.vaistramanagement.vaistramanagement.dto.EquipmentDto;
import com.vaistramanagement.vaistramanagement.dto.HttpResponse;
import com.vaistramanagement.vaistramanagement.dto.VehicleDto;
import com.vaistramanagement.vaistramanagement.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("vehicle")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<VehicleDto> addVehicle(@Valid @RequestBody VehicleDto vehicleDto) {
        return new ResponseEntity<>(vehicleService.addVehicle(vehicleDto), HttpStatus.CREATED);
    }

    @GetMapping("all")
    public ResponseEntity<List<VehicleDto>> getAllvehicle(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                              @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                              @RequestParam(value = "sortBy", defaultValue = "Id", required = false) String sortBy,
                                                              @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection) {

        return new ResponseEntity<>(vehicleService.getAllVehicle(pageNumber, pageSize, sortBy, sortDirection), HttpStatus.OK);
    }

    @GetMapping("{Id}")
    public ResponseEntity<VehicleDto> getVehicleById(@PathVariable int Id)
    {
        return new ResponseEntity<>(vehicleService.getVehicleById(Id), HttpStatus.FOUND);
    }


    @PutMapping("{Id}")
    public ResponseEntity<VehicleDto> updateVehicle(@RequestBody VehicleDto vehicleDto , @PathVariable int Id)
    {
        return new ResponseEntity<>(vehicleService.updateVehicle(vehicleDto, Id), HttpStatus.OK);
    }
    //
//
//
    @DeleteMapping("{Id}")
    public ResponseEntity<String> hardDeleteVehicleById(@PathVariable int Id)
    {
        return new ResponseEntity<>(vehicleService.deleteVehicleById(Id), HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<HttpResponse> searchByKeyword(@RequestParam(value = "keyword", defaultValue = "", required = false) String keyword,
                                                        @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                        @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                        @RequestParam(value = "sortBy", defaultValue = "Id", required = false) String sortBy,
                                                        @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection)
    {
        return new ResponseEntity<>(vehicleService.searchVehicle(keyword, pageNumber, pageSize, sortBy, sortDirection), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public String uploadCsvFile(@RequestParam("file") MultipartFile file) throws IOException {
        vehicleService.uploadCsv(file);
        return "CSV file uploaded and data inserted into the database.";
    }
    }
