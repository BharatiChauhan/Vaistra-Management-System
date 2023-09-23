package com.vaistramanagement.vaistramanagement.service;

import com.vaistramanagement.vaistramanagement.dto.DistrictDto;
import com.vaistramanagement.vaistramanagement.dto.HttpResponse;
import com.vaistramanagement.vaistramanagement.dto.SubDistrictDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SubDistrictService
{


    SubDistrictDto addSubDistrict(SubDistrictDto subdistrictDto);
    SubDistrictDto getSubDistrictById(int id);
    List<SubDistrictDto> getAllSubDistricts(int pageNumber, int pageSize, String sortBy, String sortDirection);

    SubDistrictDto updateSubDistrict(SubDistrictDto districtDto, int id);

    String deleteSubDistrictById(int subdistrictId);

    HttpResponse searchSubDistrict(String keyword, int pageNumber, int pageSize, String sortBy, String sortDirection);

    void uploadCsv(MultipartFile file) throws IOException;




//


//
//    SubDistrictDto updateSubDistrict(SubDistrictDto subdistrictDto, int id);
//
//    String deleteSubDistrictById(int id);
//
////    String softDeleteDistrictById(int id);
////
////    String restoreDistrictById(int id);
//
//    List<SubDistrictDto> getSubDistrictsByDistrictId(int districtId);
//
////    List<SubDistrictDto> getDistrictsByStateId(int stateId);
//
//    HttpResponse searchSubDistrict(String keyword, int pageNumber, int pageSize, String sortBy, String sortDirection);






//    List<SubDistrictDto> getAllSubDistrictsByActiveState(int pageNumber, int pageSize, String sortBy, String sortDirection);


//   List<SubDistrictDto> getSubDistrictsByCountryId(int countryId);
}
