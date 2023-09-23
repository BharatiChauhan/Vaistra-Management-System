package com.vaistramanagement.vaistramanagement.service;

import com.vaistramanagement.vaistramanagement.dto.DesignationDto;
import com.vaistramanagement.vaistramanagement.dto.EquipmentDto;
import com.vaistramanagement.vaistramanagement.dto.HttpResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface  EquipmentService
{

   EquipmentDto addEquipment(EquipmentDto equipmentDto);


   List<EquipmentDto> getAllEquipment(int pageNumber, int pageSize, String sortBy, String sortDirection);


    EquipmentDto getEquipmentById(int Id);


    EquipmentDto updateEquipment( EquipmentDto equipmentDto, int Id);


    String deleteEquipmentById(int Id);


    HttpResponse searchEquipment(String keyword, int pageNumber, int pageSize, String sortBy, String sortDirection);

    void uploadCsv(MultipartFile file) throws IOException;
}
