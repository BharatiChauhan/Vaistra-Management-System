package com.vaistramanagement.vaistramanagement.controller;


import com.vaistramanagement.vaistramanagement.dto.EntityDto;
import com.vaistramanagement.vaistramanagement.dto.HttpResponse;
import com.vaistramanagement.vaistramanagement.dto.MineralDto;
import com.vaistramanagement.vaistramanagement.service.EntityService;
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
@RequestMapping("entity")
public class EntityController
{

    private final EntityService entityService;

    @Autowired
    public EntityController(EntityService entityService) {
        this.entityService = entityService;
    }


    @PostMapping
    public ResponseEntity<EntityDto> addEntity(@Valid @RequestBody EntityDto entityDto) {
        return new ResponseEntity<>(entityService.addEntity(entityDto), HttpStatus.CREATED);
    }

    @GetMapping("all")
    public ResponseEntity<List<EntityDto>> getAllEntity(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                           @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                           @RequestParam(value = "sortBy", defaultValue = "entityId", required = false) String sortBy,
                                                           @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection) {

        return new ResponseEntity<>(entityService.getAllEntity(pageNumber, pageSize, sortBy, sortDirection), HttpStatus.OK);
    }

    @GetMapping("{entityId}")
    public ResponseEntity<EntityDto> getEntityById(@PathVariable int entityId)
    {
        return new ResponseEntity<>(entityService.getEntityById(entityId), HttpStatus.FOUND);
    }

    @PutMapping("{entityId}")
    public ResponseEntity<EntityDto> updateEntity(@RequestBody EntityDto entityDto  , @PathVariable int entityId)
    {
        return new ResponseEntity<>(entityService.updateEntity(entityDto, entityId), HttpStatus.OK);
    }
    //
//
//
    @DeleteMapping("{entityId}")
    public ResponseEntity<String> hardDeleteEntityById(@PathVariable int entityId)
    {
        return new ResponseEntity<>(entityService.deleteEntityById(entityId), HttpStatus.OK);
    }



    @GetMapping("search")
    public ResponseEntity<HttpResponse> searchByKeyword(@RequestParam(value = "keyword", defaultValue = "", required = false) String keyword,
                                                        @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                        @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                        @RequestParam(value = "sortBy", defaultValue = "entityId", required = false) String sortBy,
                                                        @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection)
    {
        return new ResponseEntity<>(entityService.searchEntity(keyword, pageNumber, pageSize, sortBy, sortDirection), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public String uploadCsvFile(@RequestParam("file") MultipartFile file) throws IOException {
        entityService.uploadCsv(file);
        return "CSV file uploaded and data inserted into the database.";
    }

}
