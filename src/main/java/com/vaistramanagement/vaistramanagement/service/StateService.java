package com.vaistramanagement.vaistramanagement.service;




import com.vaistramanagement.vaistramanagement.dto.StateDto;
import com.vaistramanagement.vaistramanagement.dto.HttpResponse;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

public interface StateService {
    StateDto addState(StateDto stateDto);

    StateDto getStateById(int id);

    HttpResponse getAllStates(int pageNumber, int pageSize, String sortBy, String sortDirection);
    HttpResponse getAllStatesByActiveCountry(int pageNumber, int pageSize, String sortBy, String sortDirection);

    StateDto updateState(StateDto stateDto, int id);

    String deleteStateById(int id);

    String softDeleteStateById(int id);

    String restoreStateById(int id);

    HttpResponse getStatesByCountryId(int countryId, int pageNumber, int pageSize, String sortBy, String sortDirection);

    HttpResponse searchStateByKeyword(String keyword, int pageNumber, int pageSize, String sortBy, String sortDirection);

    void uploadCsv(MultipartFile file) throws IOException;
}

