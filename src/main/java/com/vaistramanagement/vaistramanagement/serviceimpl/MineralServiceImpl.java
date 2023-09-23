package com.vaistramanagement.vaistramanagement.serviceimpl;


import com.vaistramanagement.vaistramanagement.dto.HttpResponse;
import com.vaistramanagement.vaistramanagement.dto.MineralDto;
import com.vaistramanagement.vaistramanagement.dto.SubDistrictDto;
import com.vaistramanagement.vaistramanagement.entity.Bank;
import com.vaistramanagement.vaistramanagement.entity.Mineral;
import com.vaistramanagement.vaistramanagement.entity.SubDistrict;
import com.vaistramanagement.vaistramanagement.exception.DuplicateEntryException;
import com.vaistramanagement.vaistramanagement.exception.ResourceNotFoundException;
import com.vaistramanagement.vaistramanagement.repositories.MineralRepository;
import com.vaistramanagement.vaistramanagement.service.MineralService;
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
public class MineralServiceImpl implements MineralService {


    private final MineralRepository mineralRepository;
    private final AppUtils appUtils;

    public MineralServiceImpl(MineralRepository mineralRepository, AppUtils appUtils) {
        this.mineralRepository = mineralRepository;
        this.appUtils = appUtils;
    }

    @Override
    public MineralDto addMineral(MineralDto mineral) {


            mineral.setMineralName(mineral.getMineralName().toUpperCase().trim());

            // HANDLE DUPLICATE NAME ENTRY EXCEPTION
            if(mineralRepository.existsByMineralName(mineral.getMineralName()))
                throw new DuplicateEntryException("Mineral with name '"+mineral.getMineralName()+"' already exist!");

           Mineral min  = new Mineral();
           min.setMineralName(mineral.getMineralName());
           min.setMineralCategory(mineral.getMineralCategory());
           min.setAtrName(mineral.getAtrName());
           min.setHsnCode(mineral.getHsnCode());
           min.setGrade(mineral.getGrade());

            return appUtils.mineralToDto(mineralRepository.save(min));
        }

    @Override
    public List<MineralDto> getAllMinerals(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Mineral> pageMineral = mineralRepository.findAll(pageable);
        return appUtils.mineralsToDtos(pageMineral.getContent());
    }

    @Override
    public MineralDto getMineralById(int id) {
        return appUtils.mineralToDto(mineralRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Mineral with id '"+id+"' not found!")));
    }

    @Override
    public MineralDto updateMineral(MineralDto mineralDto, int id) {
        Mineral mineral = mineralRepository.findById(id)

                .orElseThrow(() -> new ResourceNotFoundException("Mineral with Id '" + id + "' not found!"));


        // HANDLE DUPLICATE ENTRY EXCEPTION
        Mineral existedMineral = mineralRepository.findByMineralName(mineralDto.getMineralName());
//        if(existedMineral != null)
//            throw new DuplicateEntryException("Mineral with name '"+mineralDto.getMineralName()+"' already exist!");



        mineral.setMineralName(mineralDto.getMineralName().toUpperCase());
       mineral.setMineralCategory(mineralDto.getMineralCategory());
       mineral.setHsnCode(mineralDto.getHsnCode());
       mineral.setAtrName(mineralDto.getAtrName());
       mineral.setGrade(mineralDto.getGrade());


        return appUtils.mineralToDto(mineralRepository.save(mineral));

    }


    @Override
    public String deleteMineralById(int id) {
        mineralRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Mineral with id '"+id+"' not found!"));
       mineralRepository.deleteById(id);
        return "Mineral with id '"+id+"' deleted!";
    }


    @Override
    public HttpResponse searchMineral(String keyword, int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Mineral> pageMineral = mineralRepository.findByMineralNameContainingIgnoreCase(keyword, pageable);
        List<MineralDto> dtos = appUtils.mineralsToDtos(pageMineral.getContent());
        return HttpResponse.builder()
                .pageNumber(pageMineral.getNumber())
                .pageSize(pageMineral.getSize())
                .totalElements(pageMineral.getTotalElements())
                .totalPages(pageMineral.getTotalPages())
                .isLastPage(pageMineral.isLast())
                .data(dtos)
                .build();
    }

    public void uploadCsv(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            List<Mineral> dataList = reader.lines()
                    .skip(1) // Skip header row if needed
                    .map(line -> {
                        String[] parts = line.split(",");
                        Mineral csvData = new Mineral();

//                        csvData.setCountryId(Integer.parseInt(parts[0]));
                        csvData.setAtrName(parts[1]);
                        csvData.setGrade(parts[2]);
                        csvData.setHsnCode(Integer.valueOf(parts[3]));
                        csvData.setMineralCategory(parts[4]);
                        csvData.setMineralName(parts[5]);


                        // Set other columns as needed
                        return csvData;
                    })
                    .toList();
            mineralRepository.saveAll(dataList);
        }
    }


    }

