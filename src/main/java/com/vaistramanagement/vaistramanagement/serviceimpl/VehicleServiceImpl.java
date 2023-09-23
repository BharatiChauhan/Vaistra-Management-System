package com.vaistramanagement.vaistramanagement.serviceimpl;

import com.vaistramanagement.vaistramanagement.dto.EquipmentDto;
import com.vaistramanagement.vaistramanagement.dto.HttpResponse;
import com.vaistramanagement.vaistramanagement.dto.VehicleDto;
import com.vaistramanagement.vaistramanagement.entity.Bank;
import com.vaistramanagement.vaistramanagement.entity.Equipment;
import com.vaistramanagement.vaistramanagement.entity.Vehicle;
import com.vaistramanagement.vaistramanagement.exception.DuplicateEntryException;
import com.vaistramanagement.vaistramanagement.exception.ResourceNotFoundException;
import com.vaistramanagement.vaistramanagement.repositories.VehicleRepository;
import com.vaistramanagement.vaistramanagement.service.VehicleService;
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
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final AppUtils appUtils;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, AppUtils appUtils) {
        this.vehicleRepository = vehicleRepository;
        this.appUtils = appUtils;
    }

    @Override
    public VehicleDto addVehicle(VehicleDto dto) {


        dto.setVehicleName(dto.getVehicleName().toUpperCase().trim());

        // HANDLE DUPLICATE NAME ENTRY EXCEPTION
        if(vehicleRepository.existsByVehicleName(dto.getVehicleName()))
            throw new DuplicateEntryException("Vehicle with name '"+dto.getVehicleName()+"' already exist!");

        Vehicle v = new Vehicle();
        v.setVehicleName(dto.getVehicleName());


        return appUtils.vehicleToDto(vehicleRepository.save(v));
    }

    @Override
    public List<VehicleDto> getAllVehicle(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Vehicle> pageVehicle =vehicleRepository.findAll(pageable);
        return appUtils.vehiclesToDtos(pageVehicle.getContent());
    }

    @Override
    public VehicleDto getVehicleById(int Id) {
        return appUtils.vehicleToDto(vehicleRepository.findById(Id)
                .orElseThrow(()->new ResourceNotFoundException("Vehicle with id '"+Id+"' not found!")));
    }

    @Override
    public VehicleDto updateVehicle(VehicleDto vehicleDto , int Id) {
        Vehicle vehicle = vehicleRepository.findById(Id)

                .orElseThrow(() -> new ResourceNotFoundException("Vehicle with Id '" + Id + "' not found!"));


        // HANDLE DUPLICATE ENTRY EXCEPTION
       Vehicle vehicle1 = vehicleRepository.findByVehicleName(vehicleDto.getVehicleName());
//        if(existedMineral != null)
//            throw new DuplicateEntryException("Mineral with name '"+mineralDto.getMineralName()+"' already exist!");



        vehicle.setVehicleName(vehicleDto.getVehicleName().toUpperCase());



        return appUtils.vehicleToDto(vehicleRepository.save(vehicle));

    }


    @Override
    public String deleteVehicleById(int Id) {
        vehicleRepository.findById(Id)
                .orElseThrow(()->new ResourceNotFoundException("Vehicle with id '"+Id+"' not found!"));
        vehicleRepository.deleteById(Id);
        return "Vehicle with id '"+Id+"' deleted!";
    }

    @Override
    public HttpResponse searchVehicle(String keyword, int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Vehicle> pageVehicle = vehicleRepository.findByVehicleNameContainingIgnoreCase(keyword, pageable);

        List<VehicleDto> dtos = appUtils.vehiclesToDtos(pageVehicle.getContent());
        return HttpResponse.builder()
                .pageNumber(pageVehicle.getNumber())
                .pageSize(pageVehicle.getSize())
                .totalElements(pageVehicle.getTotalElements())
                .totalPages(pageVehicle.getTotalPages())
                .isLastPage(pageVehicle.isLast())
                .data(dtos)
                .build();
    }

    public void uploadCsv(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            List<Vehicle> dataList = reader.lines()
                    .skip(1) // Skip header row if needed
                    .map(line -> {
                        String[] parts = line.split(",");
                        Vehicle csvData = new Vehicle();

//                        csvData.setCountryId(Integer.parseInt(parts[0]));
                        csvData.setVehicleName(parts[1]);


                        // Set other columns as needed
                        return csvData;
                    })
                    .toList();
            vehicleRepository.saveAll(dataList);
        }
    }
}
