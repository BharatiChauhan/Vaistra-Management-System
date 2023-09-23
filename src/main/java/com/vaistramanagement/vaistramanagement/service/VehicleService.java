package com.vaistramanagement.vaistramanagement.service;

import com.vaistramanagement.vaistramanagement.dto.EquipmentDto;
import com.vaistramanagement.vaistramanagement.dto.HttpResponse;
import com.vaistramanagement.vaistramanagement.dto.VehicleDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VehicleService
{
    VehicleDto addVehicle(VehicleDto vehicleDto);


    List<VehicleDto> getAllVehicle(int pageNumber, int pageSize, String sortBy, String sortDirection);


    VehicleDto getVehicleById(int Id);


    VehicleDto updateVehicle( VehicleDto vehicleDto, int Id);


    String deleteVehicleById(int Id);


    HttpResponse searchVehicle(String keyword, int pageNumber, int pageSize, String sortBy, String sortDirection);

    void uploadCsv(MultipartFile file) throws IOException;

}
