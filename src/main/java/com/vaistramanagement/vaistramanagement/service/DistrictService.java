package com.vaistramanagement.vaistramanagement.service;

import com.vaistramanagement.vaistramanagement.dto.DistrictDto;
import com.vaistramanagement.vaistramanagement.dto.HttpResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DistrictService {
    DistrictDto addDistrict(DistrictDto districtDto);

    DistrictDto getDistrictById(int id);

    List<DistrictDto> getAllDistricts(int pageNumber, int pageSize, String sortBy, String sortDirection);
    List<DistrictDto> getAllDistrictsByActiveState(int pageNumber, int pageSize, String sortBy, String sortDirection);

    DistrictDto updateDistrict(DistrictDto districtDto, int id);

    String deleteDistrictById(int id);

//    String softDeleteDistrictById(int id);
//
//    String restoreDistrictById(int id);

    List<DistrictDto> getDistrictsByStateId(int stateId);

    List<DistrictDto> getDistrictsByCountryId(int countryId);

    HttpResponse searchDistrict(String keyword, int pageNumber, int pageSize, String sortBy, String sortDirection);

    void uploadCsv(MultipartFile file) throws IOException;
}

