package com.vaistramanagement.vaistramanagement.service;

import com.vaistramanagement.vaistramanagement.dto.EntityDto;
import com.vaistramanagement.vaistramanagement.dto.HttpResponse;
import com.vaistramanagement.vaistramanagement.dto.MineralDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EntityService
{

    EntityDto addEntity(EntityDto entityDto);




    List<EntityDto> getAllEntity(int pageNumber, int pageSize, String sortBy, String sortDirection);


    EntityDto getEntityById(int entityid);

    EntityDto updateEntity(EntityDto entityDto , int entityId);

    String deleteEntityById(int entityId);
    HttpResponse searchEntity(String keyword, int pageNumber, int pageSize, String sortBy, String sortDirection);

    void uploadCsv(MultipartFile file) throws IOException;
}



