package com.vaistramanagement.vaistramanagement.serviceimpl;

import com.vaistramanagement.vaistramanagement.dto.HttpResponse;
import com.vaistramanagement.vaistramanagement.dto.SubDistrictDto;
import com.vaistramanagement.vaistramanagement.entity.*;
import com.vaistramanagement.vaistramanagement.exception.DuplicateEntryException;
import com.vaistramanagement.vaistramanagement.exception.InactiveStatusException;
import com.vaistramanagement.vaistramanagement.exception.ResourceNotFoundException;
import com.vaistramanagement.vaistramanagement.repositories.CountryRepository;
import com.vaistramanagement.vaistramanagement.repositories.DistrictRepository;
import com.vaistramanagement.vaistramanagement.repositories.StateRepository;
import com.vaistramanagement.vaistramanagement.repositories.SubDistrictRepository;
import com.vaistramanagement.vaistramanagement.service.SubDistrictService;
import com.vaistramanagement.vaistramanagement.utils.AppUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class SubDistrictServiceImpl implements SubDistrictService
{
    private final SubDistrictRepository subdistrictRepository;

    private final DistrictRepository districtRepository;

    private final StateRepository stateRepository;
    private final CountryRepository countryRepository;
    private final AppUtils appUtils;

    public SubDistrictServiceImpl(SubDistrictRepository subdistrictRepository, DistrictRepository districtRepository, StateRepository stateRepository, CountryRepository countryRepository, AppUtils appUtils) {
        this.subdistrictRepository = subdistrictRepository;

        this.districtRepository = districtRepository;
        this.stateRepository = stateRepository;
        this.countryRepository = countryRepository;
        this.appUtils = appUtils;
    }


    @Override
    public SubDistrictDto addSubDistrict(SubDistrictDto subdistrictDto) {

        subdistrictDto.setSubDistrictName(subdistrictDto.getSubDistrictName().trim().toUpperCase());

        // HANDLE IF DUPLICATE DISTRICT NAME
        if(subdistrictRepository.existsBySubDistrictName(subdistrictDto.getSubDistrictName()))
            throw new DuplicateEntryException("SubDistrict with name '"+subdistrictDto.getDistrictName()+"' already exist!");

        // HANDLE IF STATE EXIST BY ID
        District district = districtRepository.findById(subdistrictDto.getDistrictId())
                .orElseThrow(()->new ResourceNotFoundException("District with Id '"+subdistrictDto.getDistrictId()+" not found!"));

        Country country=countryRepository.findById(subdistrictDto.getCountryId())
                .orElseThrow(()->new ResourceNotFoundException("Country with Id '"+subdistrictDto.getCountryId()+" not found!"));

        State state=stateRepository.findById(subdistrictDto.getStateId())
                .orElseThrow(()->new ResourceNotFoundException("State with Id '"+subdistrictDto.getStateId()+" not found!"));

        //  IS STATE STATUS ACTIVE ?
        if(!district.isStatus())
            throw new InactiveStatusException("SubDistrict with id '"+subdistrictDto.getDistrictId()+"' is not active!");


        SubDistrict subdistrict = new SubDistrict();
        subdistrict.setSubDistrictName(subdistrictDto.getSubDistrictName());
        subdistrict.setDistrict(district);
        subdistrict.setCountry(country);
        subdistrict.setState(state);
       subdistrict.setStatus(true);

        return appUtils.subdistrictToDto(subdistrictRepository.save(subdistrict));
    }

    @Override
    public SubDistrictDto getSubDistrictById(int id) {
        return appUtils.subdistrictToDto(subdistrictRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("SubDistrict with id '"+id+"' not found!")));
    }




    @Override
    public List<SubDistrictDto> getAllSubDistricts(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<SubDistrict> pageSubDistrict = subdistrictRepository.findAll(pageable);
        return appUtils.subdistrictsToDtos(pageSubDistrict.getContent());
    }






    @Override
    public SubDistrictDto updateSubDistrict(SubDistrictDto districtDto, int id) {

        districtDto.setSubDistrictName(districtDto.getSubDistrictName().trim().toUpperCase());
        // HANDLE IF DISTRICT EXIST BY ID
        SubDistrict district = subdistrictRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("SubDistrict with id '"+id+"' not found!"));


        District district1=districtRepository.findById(districtDto.getDistrictId())
                .orElseThrow(()->new ResourceNotFoundException("District with ID '"+districtDto.getDistrictId()+"' not found!"));

        Country country = countryRepository.findById(districtDto.getCountryId())
                .orElseThrow(()->new ResourceNotFoundException("Country with ID '"+districtDto.getCountryId()+"' not found!"));


        State state = stateRepository.findById(districtDto.getStateId())
                .orElseThrow(()->new ResourceNotFoundException("Country with ID '"+districtDto.getStateId()+"' not found!"));



        // HANDLE IF DUPLICATE DISTRICT NAME
        if(subdistrictRepository.existsBySubDistrictName(districtDto.getSubDistrictName()))
            throw new DuplicateEntryException("SubDistrict with name '"+districtDto.getSubDistrictName()+"' already exist!");

        district.setSubDistrictName(districtDto.getSubDistrictName());

        return appUtils.subdistrictToDto(subdistrictRepository.save(district));
    }


    @Override
    public String deleteSubDistrictById(int id) {
        subdistrictRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("SubDistrict with id '"+id+"' not found!"));
        subdistrictRepository.deleteById(id);
        return "SubDistrict with id '"+id+"' deleted!";
    }


    @Override
    public HttpResponse searchSubDistrict(String keyword, int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<SubDistrict> pageSubDistrict = subdistrictRepository.findBySubDistrictNameContainingIgnoreCase(keyword, pageable);
        List<SubDistrictDto> dtos = appUtils.subdistrictsToDtos(pageSubDistrict.getContent());
        return HttpResponse.builder()
                .pageNumber(pageSubDistrict.getNumber())
                .pageSize(pageSubDistrict.getSize())
                .totalElements(pageSubDistrict.getTotalElements())
                .totalPages(pageSubDistrict.getTotalPages())
                .isLastPage(pageSubDistrict.isLast())
                .data(dtos)
                .build();
    }

    public void uploadCsv(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            List<SubDistrict> dataList = reader.lines()
                    .skip(1) // Skip header row if needed
                    .map(line -> {
                        String[] parts = line.split(",");
                        SubDistrict csvData = new SubDistrict();

//                        csvData.setCountryId(Integer.parseInt(parts[0]));

                        csvData.setStatus(Boolean.parseBoolean(parts[1]));
                        csvData.setSubDistrictName(parts[1]);

                        // Set other columns as needed
                        return csvData;
                    })
                    .toList();
            subdistrictRepository.saveAll(dataList);
        }
    }


}
