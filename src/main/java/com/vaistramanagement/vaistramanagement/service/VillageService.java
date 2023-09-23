package com.vaistramanagement.vaistramanagement.service;


import com.vaistramanagement.vaistramanagement.dto.HttpResponse;
import com.vaistramanagement.vaistramanagement.dto.VillageDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface VillageService {

    VillageDto addVillage(VillageDto villageDto);

//    List<VillageDto> getAllVillage(int pageNumber, int pageSize, String sortBy, String sortDirection);

    HttpResponse getAllVillage(int pageNumber, int pageSize, String sortBy, String sortDirection);

    VillageDto getVillageById(int id);

    VillageDto updateVillage(VillageDto villageDto, int id);

    void uploadCsv(MultipartFile file) throws IOException;

    String deleteVillageById(int villageId);

    HttpResponse searchVillage(String keyword, int pageNumber, int pageSize, String sortBy, String sortDirection);



//    String deleteVillageById(int id);
//
//    HttpResponse searchVillage(String keyword, int pageNumber, int pageSize, String sortBy, String sortDirection);
//

//    List<VillageDto> getAllVillages(int pageNumber, int pageSize, String sortBy, String sortDirection);
}
