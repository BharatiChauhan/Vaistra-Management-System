package com.vaistramanagement.vaistramanagement.service;

import com.vaistramanagement.vaistramanagement.dto.HttpResponse;
import com.vaistramanagement.vaistramanagement.dto.MineralDto;
import com.vaistramanagement.vaistramanagement.dto.SubDistrictDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MineralService {

    MineralDto addMineral(MineralDto mineral);




    List<MineralDto> getAllMinerals(int pageNumber, int pageSize, String sortBy, String sortDirection);


    MineralDto getMineralById(int mineralid);

   MineralDto updateMineral(MineralDto mineralDto, int id);

    String deleteMineralById(int mineralId);
    HttpResponse searchMineral(String keyword, int pageNumber, int pageSize, String sortBy, String sortDirection);

    void uploadCsv(MultipartFile file) throws IOException;
}
