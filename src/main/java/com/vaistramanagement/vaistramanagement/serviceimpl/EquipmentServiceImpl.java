package com.vaistramanagement.vaistramanagement.serviceimpl;

import com.vaistramanagement.vaistramanagement.dto.DesignationDto;
import com.vaistramanagement.vaistramanagement.dto.EquipmentDto;
import com.vaistramanagement.vaistramanagement.dto.HttpResponse;
import com.vaistramanagement.vaistramanagement.entity.Bank;
import com.vaistramanagement.vaistramanagement.entity.Designation;
import com.vaistramanagement.vaistramanagement.entity.Equipment;
import com.vaistramanagement.vaistramanagement.exception.DuplicateEntryException;
import com.vaistramanagement.vaistramanagement.exception.ResourceNotFoundException;
import com.vaistramanagement.vaistramanagement.repositories.EquipmentRepository;
import com.vaistramanagement.vaistramanagement.service.EquipmentService;
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
public class EquipmentServiceImpl implements EquipmentService
{
    private final EquipmentRepository equipmentRepository;
    private final AppUtils appUtils;

    public EquipmentServiceImpl(EquipmentRepository equipmentRepository, AppUtils appUtils) {
        this.equipmentRepository = equipmentRepository;
        this.appUtils = appUtils;
    }

    @Override
    public EquipmentDto addEquipment(EquipmentDto dto) {


        dto.setEquipmentName(dto.getEquipmentName().toUpperCase().trim());

        // HANDLE DUPLICATE NAME ENTRY EXCEPTION
        if(equipmentRepository.existsByEquipmentName(dto.getEquipmentName()))
            throw new DuplicateEntryException("Equipment with name '"+dto.getEquipmentName()+"' already exist!");

       Equipment e = new Equipment();
        e.setEquipmentName(dto.getEquipmentName());


        return appUtils.equipToDto(equipmentRepository.save(e));
    }

    @Override
    public List<EquipmentDto> getAllEquipment(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Equipment> pageEquipment =equipmentRepository.findAll(pageable);
        return appUtils.equipsToDtos(pageEquipment.getContent());
    }

    @Override
    public EquipmentDto getEquipmentById(int Id) {
        return appUtils.equipToDto(equipmentRepository.findById(Id)
                .orElseThrow(()->new ResourceNotFoundException("Equipment with id '"+Id+"' not found!")));
    }

    @Override
    public EquipmentDto updateEquipment(EquipmentDto equipmentDto , int Id) {
     Equipment equipment = equipmentRepository.findById(Id)

                .orElseThrow(() -> new ResourceNotFoundException("Equipment with Id '" + Id + "' not found!"));


        // HANDLE DUPLICATE ENTRY EXCEPTION
        Equipment equipment1 = equipmentRepository.findByEquipmentName(equipmentDto.getEquipmentName());
//        if(existedMineral != null)
//            throw new DuplicateEntryException("Mineral with name '"+mineralDto.getMineralName()+"' already exist!");



      equipment.setEquipmentName(equipmentDto.getEquipmentName().toUpperCase());



        return appUtils.equipToDto(equipmentRepository.save(equipment));

    }


    @Override
    public String deleteEquipmentById(int Id) {
        equipmentRepository.findById(Id)
                .orElseThrow(()->new ResourceNotFoundException("Equipment with id '"+Id+"' not found!"));
        equipmentRepository.deleteById(Id);
        return "Equipment with id '"+Id+"' deleted!";
    }

    @Override
    public HttpResponse searchEquipment(String keyword, int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Equipment> pageEquipment = equipmentRepository.findByEquipmentNameContainingIgnoreCase(keyword, pageable);

        List<EquipmentDto> dtos = appUtils.equipsToDtos(pageEquipment.getContent());
        return HttpResponse.builder()
                .pageNumber(pageEquipment.getNumber())
                .pageSize(pageEquipment.getSize())
                .totalElements(pageEquipment.getTotalElements())
                .totalPages(pageEquipment.getTotalPages())
                .isLastPage(pageEquipment.isLast())
                .data(dtos)
                .build();
    }

    public void uploadCsv(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            List<Equipment> dataList = reader.lines()
                    .skip(1) // Skip header row if needed
                    .map(line -> {
                        String[] parts = line.split(",");
                        Equipment csvData = new Equipment();

//                        csvData.setCountryId(Integer.parseInt(parts[0]));
                        csvData.setEquipmentName(parts[1]);


                        // Set other columns as needed
                        return csvData;
                    })
                    .toList();
            equipmentRepository.saveAll(dataList);
        }
    }
}
