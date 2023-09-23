package com.vaistramanagement.vaistramanagement.service;

import com.vaistramanagement.vaistramanagement.dto.CountryDto;
import com.vaistramanagement.vaistramanagement.dto.HttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface CountryService {
    CountryDto addCountry(CountryDto countryDto);

    CountryDto getCountryById(int id);

//    List<CountryDto> getAllCountries(int pageNumber, int pageSize, String sortBy, String sortDirection);

    HttpResponse getAllCountries( int pageNumber, int pageSize, String sortBy, String sortDirection);

    List<CountryDto> getAllCountriesByActive(int pageNumber, int pageSize, String sortBy, String sortDirection);

    CountryDto updateCountry(CountryDto country, int id);

    String deleteCountryById(int id);


    String softDeleteCountryById(int id);

    String restoreCountryById(int id);

    HttpResponse searchCountry(String keyword, int pageNumber, int pageSize, String sortBy, String sortDirection);


    void uploadCsv(MultipartFile file) throws IOException;
}

