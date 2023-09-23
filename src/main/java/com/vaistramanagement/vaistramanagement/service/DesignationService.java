package com.vaistramanagement.vaistramanagement.service;

import com.vaistramanagement.vaistramanagement.dto.DesignationDto;
import com.vaistramanagement.vaistramanagement.dto.EntityDto;
import com.vaistramanagement.vaistramanagement.dto.HttpResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DesignationService
{
   DesignationDto addDesignation(DesignationDto designationDto);




    List<DesignationDto> getAllDesignation(int pageNumber, int pageSize, String sortBy, String sortDirection);


   DesignationDto getDesignationById(int Id);

    DesignationDto updateDesignation( DesignationDto designationDto, int Id);

    String deleteDesignationById(int Id);
    HttpResponse searchDesignation(String keyword, int pageNumber, int pageSize, String sortBy, String sortDirection);

    void uploadCsv(MultipartFile file) throws IOException;
}
