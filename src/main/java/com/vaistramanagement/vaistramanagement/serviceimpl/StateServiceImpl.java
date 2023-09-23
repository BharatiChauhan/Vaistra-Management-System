package com.vaistramanagement.vaistramanagement.serviceimpl;






import com.vaistramanagement.vaistramanagement.dto.HttpResponse;
import com.vaistramanagement.vaistramanagement.dto.StateDto;
import com.vaistramanagement.vaistramanagement.entity.Bank;
import com.vaistramanagement.vaistramanagement.entity.Country;
import com.vaistramanagement.vaistramanagement.entity.State;
import com.vaistramanagement.vaistramanagement.exception.DuplicateEntryException;
import com.vaistramanagement.vaistramanagement.exception.InactiveStatusException;
import com.vaistramanagement.vaistramanagement.exception.ResourceNotFoundException;
import com.vaistramanagement.vaistramanagement.repositories.CountryRepository;
import com.vaistramanagement.vaistramanagement.repositories.StateRepository;
import com.vaistramanagement.vaistramanagement.service.StateService;
import com.vaistramanagement.vaistramanagement.utils.AppUtils;
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
public class StateServiceImpl implements StateService {

    //---------------------------------------------------CONSTRUCTOR INJECTION------------------------------------------
    private final StateRepository stateRepository;
    private final CountryRepository countryRepository;
    private final AppUtils appUtils;


    @Autowired
    public StateServiceImpl(StateRepository stateRepository, CountryRepository countryRepository, AppUtils appUtils) {
        this.stateRepository = stateRepository;
        this.countryRepository = countryRepository;
        this.appUtils = appUtils;
    }




    //----------------------------------------------------SERVICE METHODS-----------------------------------------------
    @Override
    public StateDto addState(StateDto stateDto) {

        stateDto.setStateName(stateDto.getStateName().trim().toUpperCase());

        //  HANDLE DUPLICATE ENTRY STATE NAME
        if(stateRepository.existsByStateName(stateDto.getStateName()))
            throw new DuplicateEntryException(("State with name '"+stateDto.getStateName()+"' already exist!"));

        //  HANDLE IF COUNTRY IS NULL
        Country country = countryRepository.findById(stateDto.getCountryId())
                .orElseThrow(()->new ResourceNotFoundException("Country with ID '"+stateDto.getCountryId()+"' not found!"));

        //  IS COUNTRY STATUS ACTIVE ?
        if (!country.isStatus())
            throw new InactiveStatusException("Country with id '" + stateDto.getCountryId()+ "' is not active!");

        State state = new State();
        state.setStateName(stateDto.getStateName());
        state.setCountry(country);
        state.setStatus(true);


        return appUtils.stateToDto(stateRepository.save(state));
    }

    @Override
    public StateDto getStateById(int id) {
        return appUtils.stateToDto(stateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("State with id '" + id + "' not found!")));
    }

    @Override
    public HttpResponse getAllStates(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<State> pageState = stateRepository.findAll(pageable);
        List<StateDto> states = appUtils.statesToDtos(pageState.getContent());

        return HttpResponse.builder()
                .pageNumber(pageState.getNumber())
                .pageSize(pageState.getSize())
                .totalElements(pageState.getTotalElements())
                .totalPages(pageState.getTotalPages())
                .isLastPage(pageState.isLast())
                .data(states)
                .build();
    }
    @Override
    public HttpResponse getAllStatesByActiveCountry(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<State> pageState = stateRepository.findAllByCountry_Status(true,  pageable);

        List<StateDto> states = appUtils.statesToDtos(pageState.getContent());

        return HttpResponse.builder()
                .pageNumber(pageState.getNumber())
                .pageSize(pageState.getSize())
                .totalElements(pageState.getTotalElements())
                .totalPages(pageState.getTotalPages())
                .isLastPage(pageState.isLast())
                .data(states)
                .build();
    }

    @Override
    public HttpResponse searchStateByKeyword(String keyword, int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<State> pageState = stateRepository.findByStateNameContainingIgnoreCase(keyword,  pageable);

        List<StateDto> states = appUtils.statesToDtos(pageState.getContent());

        return HttpResponse.builder()
                .pageNumber(pageState.getNumber())
                .pageSize(pageState.getSize())
                .totalElements(pageState.getTotalElements())
                .totalPages(pageState.getTotalPages())
                .isLastPage(pageState.isLast())
                .data(states)
                .build();
    }

    @Override
    public HttpResponse getStatesByCountryId(int countryId, int pageNumber, int pageSize, String sortBy, String sortDirection) {

        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new ResourceNotFoundException("Country with id '" + countryId + "' not found!"));

        if(!country.isStatus())
            throw new InactiveStatusException("Country '"+country.getCountryName()+"' is inactive");

        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<State> pageState = stateRepository.findByCountry(country, pageable);

        List<StateDto> states = appUtils.statesToDtos(pageState.getContent());

        return HttpResponse.builder()
                .pageNumber(pageState.getNumber())
                .pageSize(pageState.getSize())
                .totalElements(pageState.getTotalElements())
                .totalPages(pageState.getTotalPages())
                .isLastPage(pageState.isLast())
                .data(states)
                .build();
    }

    @Override
    public StateDto updateState(StateDto stateDto, int id) {

        stateDto.setStateName(stateDto.getStateName().trim().toUpperCase());

        //  HANDLE IF STATE EXIST BY ID
        State state = stateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("State with id '" + id + "' not found!"));

        Country country=countryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Country with id ' " + id +" 'not found ! "));
        //  HANDLE DUPLICATE ENTRY STATE NAME
        if(stateRepository.existsByStateName(stateDto.getStateName()))
        {
            throw new DuplicateEntryException("State with name '"+stateDto.getStateName()+"' already exist!");
        }

        state.setStateName(stateDto.getStateName());
        return appUtils.stateToDto(stateRepository.save(state));
    }

    @Override
    public String deleteStateById(int id) {

        State state = stateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("State with id '" + id + "' not found!"));

        stateRepository.delete(state);
        return "State with id " + id + "' deleted!";
    }

    @Override
    public String softDeleteStateById(int id) {
//        State state = stateRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("State with id '" + id + "' not found!"));
//        state.setDeleted(true);
//        stateRepository.save(state);
//        return "State with id " + id + "' Soft deleted!";

        return null;
    }

    @Override
    public String restoreStateById(int id) {
//        State state = stateRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("State with id '" + id + "' not found!"));
//        state.setDeleted(false);
//        stateRepository.save(state);
//        return "State with id " + id + "' Restored!";

        return null;
    }

    public void uploadCsv(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            List<State> dataList = reader.lines()
                    .skip(1) // Skip header row if needed
                    .map(line -> {
                        String[] parts = line.split(",");
                        State csvData = new State();

//                        csvData.setCountryId(Integer.parseInt(parts[0]));
                        csvData.setStateName(parts[1]);
                        csvData.setStatus(Boolean.parseBoolean(parts[2]));

                        // Set other columns as needed
                        return csvData;
                    })
                    .toList();
            stateRepository.saveAll(dataList);
        }
    }


}

