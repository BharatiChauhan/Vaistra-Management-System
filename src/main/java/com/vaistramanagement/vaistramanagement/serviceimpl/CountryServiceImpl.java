package com.vaistramanagement.vaistramanagement.serviceimpl;

import com.vaistramanagement.vaistramanagement.dto.CountryDto;
import com.vaistramanagement.vaistramanagement.dto.HttpResponse;
import com.vaistramanagement.vaistramanagement.entity.Country;
import com.vaistramanagement.vaistramanagement.exception.DuplicateEntryException;
import com.vaistramanagement.vaistramanagement.exception.ResourceNotFoundException;
import com.vaistramanagement.vaistramanagement.repositories.CountryRepository;
import com.vaistramanagement.vaistramanagement.service.CountryService;
import com.vaistramanagement.vaistramanagement.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    //---------------------------------------------------CONSTRUCTOR INJECTION------------------------------------------

    private final CountryRepository countryRepository;
    private final AppUtils appUtils;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, AppUtils appUtils) {
        this.countryRepository = countryRepository;
        this.appUtils = appUtils;
    }


    //----------------------------------------------------SERVICE METHODS-----------------------------------------------

    @Override
    public CountryDto addCountry(CountryDto c) {

        c.setCountryName(c.getCountryName().toUpperCase().trim());

        // HANDLE DUPLICATE NAME ENTRY EXCEPTION
        if (countryRepository.existsByCountryName(c.getCountryName()))
            throw new DuplicateEntryException("Country with name '" + c.getCountryName() + "' already exist!");

        Country country = new Country();
        country.setCountryName(c.getCountryName());
        country.setStatus(true);

        return appUtils.countryToDto(countryRepository.save(country));
    }

    @Override
    public CountryDto getCountryById(int id) {
        return appUtils.countryToDto(countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country with id '" + id + "' Not Found!")));
    }

//    @Override
//    public List<CountryDto> getAllCountries(int pageNumber, int pageSize, String sortBy, String sortDirection) {
//        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
//                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
//
//        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
//        Page<Country> pageCountry = countryRepository.findAll(pageable);
//        return appUtils.countriesToDtos(pageCountry.getContent());
//    }


    @Override
    public HttpResponse getAllCountries( int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Country> pageCountry = countryRepository.findAll(pageable);
        List<CountryDto> countries = appUtils.countriesToDtos(pageCountry.getContent());
        return HttpResponse.builder()
                .pageNumber(pageCountry.getNumber())
                .pageSize(pageCountry.getSize())
                .totalElements(pageCountry.getTotalElements())
                .totalPages(pageCountry.getTotalPages())
                .isLastPage(pageCountry.isLast())
                .data(countries)
                .build();
    }

    @Override
    public List<CountryDto> getAllCountriesByActive(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Country> pageCountry = countryRepository.findAllByStatus(true, pageable);

        return appUtils.countriesToDtos(pageCountry.getContent());
    }

    @Override
    public HttpResponse searchCountry(String keyword, int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, Integer.MAX_VALUE, sort);

        if (keyword == null || keyword.isEmpty()) {

          throw new ResourceNotFoundException("Keyword is require");
            // Handle the case where no keywords are provided
            //
        }
        
        Integer keyword2= null;
        Boolean keyword3 = null;

        try {
            keyword2 = Integer.parseInt(keyword);
        } catch (NumberFormatException e) {
            keyword2 = null;
        }

        if(keyword.equalsIgnoreCase("true"))
            keyword3 = Boolean.TRUE;
        else if (keyword.equalsIgnoreCase("false")) {
            keyword3 = Boolean.FALSE;
        }


        Page<Country> pageCountry = countryRepository.findByCountryNameContainingIgnoreCaseOrCountryIdOrStatus( pageable,keyword,keyword2,keyword3);
        List<CountryDto> countries = appUtils.countriesToDtos(pageCountry.getContent());
        return HttpResponse.builder()
                .pageNumber(pageCountry.getNumber())
                .pageSize(pageCountry.getSize())
                .totalElements(pageCountry.getTotalElements())
                .totalPages(pageCountry.getTotalPages())
                .isLastPage(pageCountry.isLast())
                .data(countries)
                .build();
    }


    @Override
    public CountryDto updateCountry(CountryDto c, int id) {
        // HANDLE IF COUNTRY EXIST BY ID
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country with Id '" + id + "' not found!"));


        // HANDLE DUPLICATE ENTRY EXCEPTION
        Country existedCountry = countryRepository.findByCountryName(c.getCountryName());
        if (existedCountry != null)
            throw new DuplicateEntryException("Country with name '" + c.getCountryName() + "' already exist!");

        country.setCountryName(c.getCountryName().toUpperCase());

        return appUtils.countryToDto(countryRepository.save(country));

    }

    @Override
    public String deleteCountryById(int id) {
        countryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Country with Id '" + id + "' not found!"));

        countryRepository.deleteById(id);
        return "Country with Id '" + id + "' deleted";
    }

    @Override
    public String softDeleteCountryById(int id) {

//        Country country = countryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Country with Id '" + id + "' not found!"));
//        country.setDeleted(true);
//        countryRepository.save(country);
        return "Country with Id '" + id + "' Soft Deleted";

//        return null;
    }

    @Override
    public String restoreCountryById(int id) {
//        Country country = countryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Country with Id '" + id + "' not found!"));
//        country.setDeleted(false);
//        countryRepository.save(country);
        return "Country with id '" + id + "' restored!";

//        return null;

    }


    public void uploadCsv(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            List<Country> dataList = reader.lines()
                    .skip(1) // Skip header row if needed
                    .map(line -> {
                        String[] parts = line.split(",");
                        Country csvData = new Country();


//                        csvData.setCountryId(Integer.parseInt(parts[0]));
                        csvData.setCountryName(parts[1]);
                        csvData.setStatus(Boolean.parseBoolean(parts[2]));

                        // Set other columns as needed
                        return csvData;
                    })
                    .toList();
            countryRepository.saveAll(dataList);
        }

    }
}














