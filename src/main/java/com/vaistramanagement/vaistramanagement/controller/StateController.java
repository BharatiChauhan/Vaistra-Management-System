package com.vaistramanagement.vaistramanagement.controller;



import com.vaistramanagement.vaistramanagement.dto.HttpResponse;


import com.vaistramanagement.vaistramanagement.dto.StateDto;
import com.vaistramanagement.vaistramanagement.service.StateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("state")
public class StateController {


    //---------------------------------------------------CONSTRUCTOR INJECTION------------------------------------------
    private final StateService stateService;

    @Autowired
    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    //---------------------------------------------------URL ENDPOINTS--------------------------------------------------

    @PostMapping
    public ResponseEntity<StateDto> addState(@Valid @RequestBody StateDto dto) {
        return new ResponseEntity<>(stateService.addState(dto), HttpStatus.CREATED);
    }

    @GetMapping("{stateId}")
    public ResponseEntity<StateDto> getStateById(@PathVariable int stateId) {
        return new ResponseEntity<>(stateService.getStateById(stateId), HttpStatus.FOUND);
    }




    @GetMapping("all")
    public ResponseEntity<HttpResponse> getAllStates(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                     @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                     @RequestParam(value = "sortBy", defaultValue = "stateId", required = false) String sortBy,
                                                     @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection) {
        return new ResponseEntity<>(stateService.getAllStates(pageNumber, pageSize, sortBy, sortDirection), HttpStatus.FOUND);
    }
    @GetMapping
    public ResponseEntity<HttpResponse> getAllStatesByActiveCountry(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                                    @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                                    @RequestParam(value = "sortBy", defaultValue = "stateId", required = false) String sortBy,
                                                                    @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection) {
        return new ResponseEntity<>(stateService.getAllStatesByActiveCountry(pageNumber, pageSize, sortBy, sortDirection), HttpStatus.FOUND);
    }
    @GetMapping("countryId/{countryId}")
    public ResponseEntity<HttpResponse> getStateByCountryId(@PathVariable int countryId,
                                                            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                            @RequestParam(value = "sortBy", defaultValue = "stateId", required = false) String sortBy,
                                                            @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection)
    {
        return new ResponseEntity<>(stateService.getStatesByCountryId(countryId, pageNumber, pageSize, sortBy, sortDirection), HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<HttpResponse> searchStateByKeyword(@RequestParam(value = "keyword", defaultValue = "", required = false) String keyword,
                                                             @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                             @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                             @RequestParam(value = "sortBy", defaultValue = "stateId", required = false) String sortBy,
                                                             @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection)
    {
        return new ResponseEntity<>(stateService.searchStateByKeyword(keyword, pageNumber, pageSize, sortBy, sortDirection), HttpStatus.OK);
    }

    @PutMapping("{stateId}")
    public ResponseEntity<StateDto> updateState(@Valid @RequestBody StateDto stateDto, @PathVariable int stateId) {
        return new ResponseEntity<>(stateService.updateState(stateDto, stateId), HttpStatus.OK);
    }

    @DeleteMapping("{stateId}")
    public ResponseEntity<String> deleteStateById(@PathVariable int stateId) {
        return new ResponseEntity<>(stateService.deleteStateById(stateId), HttpStatus.OK);
    }


    @PostMapping("/upload")
    public String uploadCsvFile(@RequestParam("file") MultipartFile file) throws IOException {
        stateService.uploadCsv(file);
        return "CSV file uploaded and data inserted into the database.";
    }
//    @PutMapping("softDelete/{stateId}")
//    public ResponseEntity<String> softDeleteStateById(@PathVariable int stateId) {
//        return new ResponseEntity<>(stateService.softDeleteStateById(stateId), HttpStatus.OK);
//    }
//
//    @PutMapping("restore/{stateId}")
//    public ResponseEntity<String> restoreStateById(@PathVariable int stateId) {
//        return new ResponseEntity<>(stateService.restoreStateById(stateId), HttpStatus.OK);
//    }


}

