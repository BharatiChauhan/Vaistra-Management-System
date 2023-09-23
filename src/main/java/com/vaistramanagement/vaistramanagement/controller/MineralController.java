package com.vaistramanagement.vaistramanagement.controller;


import com.vaistramanagement.vaistramanagement.dto.*;
import com.vaistramanagement.vaistramanagement.service.CountryService;
import com.vaistramanagement.vaistramanagement.service.MineralService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("mineral")
public class MineralController
{

    private final MineralService mineralService;

    @Autowired
    public MineralController(MineralService mineralService) {
        this.mineralService = mineralService;
    }


    @PostMapping
    public ResponseEntity<MineralDto> addMineral(@Valid @RequestBody MineralDto mineral) {
        return new ResponseEntity<>(mineralService.addMineral(mineral), HttpStatus.CREATED);
    }

    @GetMapping("all")
    public ResponseEntity<List<MineralDto>> getAllMinerals(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                          @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                          @RequestParam(value = "sortBy", defaultValue = "mineralId", required = false) String sortBy,
                                                          @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection) {

        return new ResponseEntity<>(mineralService.getAllMinerals(pageNumber, pageSize, sortBy, sortDirection), HttpStatus.OK);
    }

    @GetMapping("{mineralId}")
    public ResponseEntity<MineralDto> getMineralById(@PathVariable int mineralId)
    {
        return new ResponseEntity<>(mineralService.getMineralById(mineralId), HttpStatus.FOUND);
    }

    @PutMapping("{mineralId}")
    public ResponseEntity<MineralDto> updateMineral(@RequestBody MineralDto mineralDto , @PathVariable int mineralId)
    {
        return new ResponseEntity<>(mineralService.updateMineral(mineralDto, mineralId), HttpStatus.OK);
    }
    //
//
//
    @DeleteMapping("{mineralId}")
    public ResponseEntity<String> hardDeleteMineralById(@PathVariable int mineralId)
    {
        return new ResponseEntity<>(mineralService.deleteMineralById(mineralId), HttpStatus.OK);
    }



    @GetMapping("search")
    public ResponseEntity<HttpResponse> searchByKeyword(@RequestParam(value = "keyword", defaultValue = "", required = false) String keyword,
                                                        @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                        @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                        @RequestParam(value = "sortBy", defaultValue = "mineralId", required = false) String sortBy,
                                                        @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection)
    {
        return new ResponseEntity<>(mineralService.searchMineral(keyword, pageNumber, pageSize, sortBy, sortDirection), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public String uploadCsvFile(@RequestParam("file") MultipartFile file) throws IOException {
        mineralService.uploadCsv(file);
        return "CSV file uploaded and data inserted into the database.";
    }

}
