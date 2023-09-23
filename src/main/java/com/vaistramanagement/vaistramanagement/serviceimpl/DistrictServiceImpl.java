package com.vaistramanagement.vaistramanagement.serviceimpl;

import com.vaistramanagement.vaistramanagement.dto.CountryDto;
import com.vaistramanagement.vaistramanagement.dto.DistrictDto;
import com.vaistramanagement.vaistramanagement.dto.HttpResponse;
import com.vaistramanagement.vaistramanagement.entity.Bank;
import com.vaistramanagement.vaistramanagement.entity.Country;
import com.vaistramanagement.vaistramanagement.entity.District;
import com.vaistramanagement.vaistramanagement.entity.State;
import com.vaistramanagement.vaistramanagement.exception.DuplicateEntryException;
import com.vaistramanagement.vaistramanagement.exception.InactiveStatusException;
import com.vaistramanagement.vaistramanagement.exception.ResourceNotFoundException;
import com.vaistramanagement.vaistramanagement.repositories.CountryRepository;
import com.vaistramanagement.vaistramanagement.repositories.DistrictRepository;
import com.vaistramanagement.vaistramanagement.repositories.StateRepository;
import com.vaistramanagement.vaistramanagement.service.DistrictService;
import com.vaistramanagement.vaistramanagement.utils.AppUtils;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
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
public class DistrictServiceImpl implements DistrictService {

    //---------------------------------------------------CONSTRUCTOR INJECTION------------------------------------------
    private final DistrictRepository districtRepository;
    private final StateRepository stateRepository;
    private final CountryRepository countryRepository;
    private final AppUtils appUtils;


    @Autowired
    public DistrictServiceImpl(DistrictRepository districtRepository, StateRepository stateRepository,
                               CountryRepository countryRepository, AppUtils appUtils)
    {
        this.districtRepository = districtRepository;
        this.stateRepository = stateRepository;
        this.countryRepository = countryRepository;
        this.appUtils = appUtils;
    }


    //----------------------------------------------------SERVICE METHODS-----------------------------------------------
    @Override
    public DistrictDto addDistrict(DistrictDto districtDto) {

        districtDto.setDistrictName(districtDto.getDistrictName().trim().toUpperCase());

        // HANDLE IF DUPLICATE DISTRICT NAME
        if(districtRepository.existsByDistrictName(districtDto.getDistrictName()))
            throw new DuplicateEntryException("District with name '"+districtDto.getDistrictName()+"' already exist!");

        // HANDLE IF STATE EXIST BY ID
        State state = stateRepository.findById(districtDto.getStateId())
                .orElseThrow(()->new ResourceNotFoundException("State with Id '"+districtDto.getStateId()+" not found!"));

        Country country=countryRepository.findById(districtDto.getCountryId())
                .orElseThrow(()->new ResourceNotFoundException("Country with Id '"+districtDto.getCountryId()+" not found!"));

        //  IS STATE STATUS ACTIVE ?
        if(!state.isStatus())
            throw new InactiveStatusException("State with id '"+districtDto.getStateId()+"' is not active!");


        District district = new District();
        district.setDistrictName(districtDto.getDistrictName());
        district.setState(state);
        district.setCountry(country);
        district.setStatus(true);

        return appUtils.districtToDto(districtRepository.save(district));
    }

    @Override
    public DistrictDto getDistrictById(int id) {
        return appUtils.districtToDto(districtRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("District with id '"+id+"' not found!")));
    }

    @Override
    public List<DistrictDto> getAllDistricts(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<District> pageDistrict = districtRepository.findAll(pageable);
        return appUtils.districtsToDtos(pageDistrict.getContent());
    }
    @Override
    public List<DistrictDto> getAllDistrictsByActiveState(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<District> pageDistrict = districtRepository.findAllByState_Status(true, pageable);
        return appUtils.districtsToDtos(pageDistrict.getContent());
    }

    @Override
    public DistrictDto updateDistrict(DistrictDto districtDto, int id) {

        districtDto.setDistrictName(districtDto.getDistrictName().trim().toUpperCase());
        // HANDLE IF DISTRICT EXIST BY ID
        District district = districtRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("District with id '"+id+"' not found!"));

        Country country = countryRepository.findById(districtDto.getCountryId())
                .orElseThrow(()->new ResourceNotFoundException("Country with ID '"+districtDto.getCountryId()+"' not found!"));


       State state = stateRepository.findById(districtDto.getStateId())
                .orElseThrow(()->new ResourceNotFoundException("State with ID '"+districtDto.getStateId()+"' not found!"));

        // HANDLE IF DUPLICATE DISTRICT NAME
        if(districtRepository.existsByDistrictName(districtDto.getDistrictName()))
            throw new DuplicateEntryException("District with name '"+districtDto.getDistrictName()+"' already exist!");

        district.setDistrictName(districtDto.getDistrictName());

        return appUtils.districtToDto(districtRepository.save(district));
    }

    @Override
    public String deleteDistrictById(int id) {
        districtRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("District with id '"+id+"' not found!"));
        districtRepository.deleteById(id);
        return "District with id '"+id+"' deleted!";
    }

//    @Override
//    public String softDeleteDistrictById(int id) {
//        District district = districtRepository.findById(id)
//                .orElseThrow(()->new ResourceNotFoundException("District with id '"+id+"' not found!"));
//        district.setDeleted(true);
//        districtRepository.save(district);
//        return "District with id '"+id+"' soft deleted!";
//    }
//
//    @Override
//    public String restoreDistrictById(int id) {
//        District district = districtRepository.findById(id)
//                .orElseThrow(()->new ResourceNotFoundException("District with id '"+id+"' not found!"));
//        district.setDeleted(false);
//        districtRepository.save(district);
//        return "District with id '"+id+"' restored!";
//    }

    @Override
    public List<DistrictDto> getDistrictsByStateId(int stateId) {

        stateRepository.findById(stateId).
                orElseThrow(()->new ResourceNotFoundException("State with id '"+stateId+"' not found!"));
        return appUtils.districtsToDtos(districtRepository.findByState_StateId(stateId));
    }

    @Override
    public List<DistrictDto> getDistrictsByCountryId(int countryId) {
        countryRepository.findById(countryId)
                .orElseThrow(()->new ResourceNotFoundException("Country with id '"+countryId+"' not found!"));
        return appUtils.districtsToDtos(districtRepository.findByState_Country_CountryId(countryId));
    }


    @Override
    public HttpResponse searchDistrict(String keyword, int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<District> pageDistrict = districtRepository.findByDistrictNameContainingIgnoreCase(keyword, pageable);
        List<DistrictDto> dtos = appUtils.districtsToDtos(pageDistrict.getContent());
        return HttpResponse.builder()
                .pageNumber(pageDistrict.getNumber())
                .pageSize(pageDistrict.getSize())
                .totalElements(pageDistrict.getTotalElements())
                .totalPages(pageDistrict.getTotalPages())
                .isLastPage(pageDistrict.isLast())
                .data(dtos)
                .build();
    }

    public void uploadCsv(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            List<District> dataList = reader.lines()
                    .skip(1) // Skip header row if needed
                    .map(line -> {
                        String[] parts = line.split(",");
                        District csvData = new District();

//                        csvData.setCountryId(Integer.parseInt(parts[0]));
                        csvData.setDistrictName(parts[1]);
                        csvData.setStatus(Boolean.parseBoolean(parts[2]));

                        // Set other columns as needed
                        return csvData;
                    })
                    .toList();
            districtRepository.saveAll(dataList);
        }
    }
}

