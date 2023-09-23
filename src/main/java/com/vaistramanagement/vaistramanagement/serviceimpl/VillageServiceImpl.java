package com.vaistramanagement.vaistramanagement.serviceimpl;


import com.vaistramanagement.vaistramanagement.dto.*;
import com.vaistramanagement.vaistramanagement.entity.*;
import com.vaistramanagement.vaistramanagement.exception.DuplicateEntryException;
import com.vaistramanagement.vaistramanagement.exception.ResourceNotFoundException;
import com.vaistramanagement.vaistramanagement.repositories.*;
import com.vaistramanagement.vaistramanagement.service.VillageService;
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
public class VillageServiceImpl implements VillageService {

    private final VillageRepository villageRepository;
    private final SubDistrictRepository subdistrictRepository;
    private final DistrictRepository districtRepository;
    private final StateRepository stateRepository;
    private final CountryRepository countryRepository;
    private final AppUtils appUtils;

    public VillageServiceImpl(VillageRepository villageRepository, SubDistrictRepository subdistrictRepository, DistrictRepository districtRepository, StateRepository stateRepository, CountryRepository countryRepository, AppUtils appUtils) {

        this.villageRepository = villageRepository;
        this.subdistrictRepository = subdistrictRepository;
        this.districtRepository = districtRepository;
        this.stateRepository = stateRepository;
        this.countryRepository = countryRepository;
        this.appUtils = appUtils;
    }


    @Override
    public VillageDto addVillage(VillageDto villageDto) {

        villageDto.setVillageName(villageDto.getVillageName().toUpperCase().trim());

        // HANDLE IF DUPLICATE DISTRICT NAME
        if(villageRepository.existsByVillageName(villageDto.getVillageName()))
            throw new DuplicateEntryException("Village with name '"+villageDto.getVillageName()+"' already exist!");

        Village village=new Village();
        // HANDLE IF STATE EXIST BY ID
        SubDistrict subdistrict = subdistrictRepository.findById(villageDto.getSubDistrictId())
                .orElseThrow(()->new ResourceNotFoundException("SubDistrict with Id '"+villageDto.getSubDistrictId()+" not found!"));

        District district1 = districtRepository.findById(villageDto.getDistrictId())
                .orElseThrow(()->new ResourceNotFoundException("District with Id '"+villageDto.getDistrictId()+" not found!"));

        Country country=countryRepository.findById(villageDto.getCountryId())
                .orElseThrow(()->new ResourceNotFoundException("Country with Id '"+villageDto.getCountryId()+" not found!"));

        State state=stateRepository.findById(villageDto.getStateId())
                .orElseThrow(()->new ResourceNotFoundException("State with Id '"+villageDto.getStateId()+" not found!"));

////          IS STATE STATUS ACTIVE ?
//        if(!district.isStatus())
//            throw new InactiveStatusException("Village with id '"+villageDto.getSubDistrictId()+"' is not active!");
//



        village.setVillageName(villageDto.getVillageName());
        village.setSubDistrict(subdistrict);
        village.setDistrict(district1);
        village.setState(state);
        village.setCountry(country);
        village.setStatus(true);

        return  appUtils.villageToDto(villageRepository.save(village));
    }





    @Override
    public HttpResponse getAllVillage(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Village> pageVillage = villageRepository.findAll(pageable);
        List<VillageDto> states = appUtils.villagesToDtos(pageVillage.getContent());

        return HttpResponse.builder()
                .pageNumber(pageVillage.getNumber())
                .pageSize(pageVillage.getSize())
                .totalElements(pageVillage.getTotalElements())
                .totalPages(pageVillage.getTotalPages())
                .isLastPage(pageVillage.isLast())
                .data(states)
                .build();
    }
    @Override
    public VillageDto getVillageById(int id) {
        return appUtils.villageToDto(villageRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Village with id '"+id+"' not found!")));
    }



    @Override
    public VillageDto updateVillage(VillageDto villageDto , int id) {

       villageDto.setVillageName(villageDto.getVillageName().trim().toUpperCase());
        // HANDLE IF DISTRICT EXIST BY ID
       Village village = villageRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Village with id '"+id+"' not found!"));

        SubDistrict district=subdistrictRepository.findById(villageDto.getSubDistrictId())
                .orElseThrow(()->new ResourceNotFoundException("Sub-District with ID '"+villageDto.getSubDistrictId()+"' not found!"));

        District district1=districtRepository.findById(villageDto.getDistrictId())
                .orElseThrow(()->new ResourceNotFoundException("District with ID '"+villageDto.getDistrictId()+"' not found!"));

        Country country = countryRepository.findById(villageDto.getCountryId())
                .orElseThrow(()->new ResourceNotFoundException("Country with ID '"+villageDto.getCountryId()+"' not found!"));


        State state = stateRepository.findById(villageDto.getStateId())
                .orElseThrow(()->new ResourceNotFoundException("Country with ID '"+villageDto.getStateId()+"' not found!"));


        // HANDLE IF DUPLICATE DISTRICT NAME
        if(villageRepository.existsByVillageName(villageDto.getVillageName()))
            throw new DuplicateEntryException("Village with name '"+villageDto.getVillageName()+"' already exist!");

           village.setVillageName(villageDto.getVillageName());

        return appUtils.villageToDto(villageRepository.save(village));
    }


    @Override
    public String deleteVillageById(int id) {
        villageRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Village with id '"+id+"' not found!"));
        villageRepository.deleteById(id);
        return "Village with id '"+id+"' deleted!";
    }


    @Override
    public HttpResponse searchVillage(String keyword, int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Village> pageVillage = villageRepository.findByVillageNameContainingIgnoreCase(keyword, pageable);
        List<VillageDto> dtos = appUtils.villagesToDtos(pageVillage.getContent());
        return HttpResponse.builder()
                .pageNumber(pageVillage.getNumber())
                .pageSize(pageVillage.getSize())
                .totalElements(pageVillage.getTotalElements())
                .totalPages(pageVillage.getTotalPages())
                .isLastPage(pageVillage.isLast())
                .data(dtos)
                .build();
    }

    public void uploadCsv(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            List<Village> dataList = reader.lines()
                    .skip(1) // Skip header row if needed
                    .map(line -> {
                        String[] parts = line.split(",");
                        Village csvData = new Village();

//                        csvData.setCountryId(Integer.parseInt(parts[0]));

                        csvData.setStatus(Boolean.parseBoolean(parts[1]));
                        csvData.setVillageName(parts[2]);


                        // Set other columns as needed
                        return csvData;
                    })
                    .toList();
            villageRepository.saveAll(dataList);
        }
    }

    }
