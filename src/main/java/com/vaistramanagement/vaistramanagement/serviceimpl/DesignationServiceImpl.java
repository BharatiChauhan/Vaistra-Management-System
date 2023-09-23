package com.vaistramanagement.vaistramanagement.serviceimpl;

import com.vaistramanagement.vaistramanagement.dto.DesignationDto;
import com.vaistramanagement.vaistramanagement.dto.EntityDto;
import com.vaistramanagement.vaistramanagement.dto.HttpResponse;
import com.vaistramanagement.vaistramanagement.entity.Bank;
import com.vaistramanagement.vaistramanagement.entity.Designation;
import com.vaistramanagement.vaistramanagement.entity.EntityType;
import com.vaistramanagement.vaistramanagement.exception.DuplicateEntryException;
import com.vaistramanagement.vaistramanagement.exception.ResourceNotFoundException;
import com.vaistramanagement.vaistramanagement.repositories.DesignationRepository;
import com.vaistramanagement.vaistramanagement.service.DesignationService;
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
public class DesignationServiceImpl implements DesignationService
{
    private final DesignationRepository designationRepository;
    private final AppUtils appUtils;


    public DesignationServiceImpl(DesignationRepository designationRepository, AppUtils appUtils) {
        this.designationRepository = designationRepository;
        this.appUtils = appUtils;
    }


    @Override
    public DesignationDto addDesignation(DesignationDto dto) {


        dto.setName(dto.getName().toUpperCase().trim());

        // HANDLE DUPLICATE NAME ENTRY EXCEPTION
        if(designationRepository.existsByName(dto.getName()))
            throw new DuplicateEntryException("Designation with name '"+dto.getName()+"' already exist!");

    Designation d  = new Designation();
     d.setName(dto.getName());


        return appUtils.desToDto(designationRepository.save(d));
    }


    @Override
    public List<DesignationDto> getAllDesignation(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Designation> pageDesignation =designationRepository.findAll(pageable);
        return appUtils.desigsToDtos(pageDesignation.getContent());
    }

    @Override
    public DesignationDto getDesignationById(int Id) {
        return appUtils.desToDto(designationRepository.findById(Id)
                .orElseThrow(()->new ResourceNotFoundException("Designation with id '"+Id+"' not found!")));
    }

    @Override
    public DesignationDto updateDesignation(DesignationDto designationDto , int Id) {
      Designation designation = designationRepository.findById(Id)

                .orElseThrow(() -> new ResourceNotFoundException("Designation with Id '" + Id + "' not found!"));


        // HANDLE DUPLICATE ENTRY EXCEPTION
 Designation type = designationRepository.findByName(designationDto.getName());
//        if(existedMineral != null)
//            throw new DuplicateEntryException("Mineral with name '"+mineralDto.getMineralName()+"' already exist!");



       designation.setName(designationDto.getName().toUpperCase());



        return appUtils.desToDto(designationRepository.save(designation));

    }


    @Override
    public String deleteDesignationById(int Id) {
     designationRepository.findById(Id)
                .orElseThrow(()->new ResourceNotFoundException("Designation with id '"+Id+"' not found!"));
        designationRepository.deleteById(Id);
        return "Designation with id '"+Id+"' deleted!";
    }

    @Override
    public HttpResponse searchDesignation(String keyword, int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Designation> pageDesignation = designationRepository.findByNameContainingIgnoreCase(keyword, pageable);

        List<DesignationDto> dtos = appUtils.desigsToDtos(pageDesignation.getContent());
        return HttpResponse.builder()
                .pageNumber(pageDesignation.getNumber())
                .pageSize(pageDesignation.getSize())
                .totalElements(pageDesignation.getTotalElements())
                .totalPages(pageDesignation.getTotalPages())
                .isLastPage(pageDesignation.isLast())
                .data(dtos)
                .build();
    }

    public void uploadCsv(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            List<Designation> dataList = reader.lines()
                    .skip(1) // Skip header row if needed
                    .map(line -> {
                        String[] parts = line.split(",");
                        Designation csvData = new Designation();

//                        csvData.setCountryId(Integer.parseInt(parts[0]));
                        csvData.setName(parts[1]);


                        // Set other columns as needed
                        return csvData;
                    })
                    .toList();
            designationRepository.saveAll(dataList);
        }
    }

}
