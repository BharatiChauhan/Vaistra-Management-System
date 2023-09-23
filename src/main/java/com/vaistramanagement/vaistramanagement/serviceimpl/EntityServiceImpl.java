package com.vaistramanagement.vaistramanagement.serviceimpl;

import com.vaistramanagement.vaistramanagement.dto.EntityDto;
import com.vaistramanagement.vaistramanagement.dto.HttpResponse;
import com.vaistramanagement.vaistramanagement.dto.MineralDto;
import com.vaistramanagement.vaistramanagement.entity.Bank;
import com.vaistramanagement.vaistramanagement.entity.EntityType;
import com.vaistramanagement.vaistramanagement.entity.Mineral;
import com.vaistramanagement.vaistramanagement.exception.DuplicateEntryException;
import com.vaistramanagement.vaistramanagement.exception.ResourceNotFoundException;
import com.vaistramanagement.vaistramanagement.repositories.EntityRepository;
import com.vaistramanagement.vaistramanagement.repositories.MineralRepository;
import com.vaistramanagement.vaistramanagement.service.EntityService;
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
public class EntityServiceImpl implements EntityService
{
    private final EntityRepository entityRepository;
    private final AppUtils appUtils;


    public EntityServiceImpl(EntityRepository entityRepository, AppUtils appUtils) {
        this.entityRepository = entityRepository;
        this.appUtils = appUtils;
    }

    @Override
    public EntityDto addEntity(EntityDto dto) {


      dto.setEntityType(dto.getEntityType().toUpperCase().trim());

        // HANDLE DUPLICATE NAME ENTRY EXCEPTION
        if(entityRepository.existsByEntityType(dto.getEntityType()))
            throw new DuplicateEntryException("Mineral with name '"+dto.getEntityType()+"' already exist!");

       EntityType en  = new EntityType();
        en.setEntityType(dto.getEntityType());
        en.setShortName(dto.getShortName());

        return appUtils.entityToDto(entityRepository.save(en));
    }


    @Override
    public List<EntityDto> getAllEntity(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<EntityType> pageEntity = entityRepository.findAll(pageable);
        return appUtils.entitysToDtos(pageEntity.getContent());
    }

    @Override
    public EntityDto getEntityById(int id) {
        return appUtils.entityToDto(entityRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Entity with id '"+id+"' not found!")));
    }

    @Override
    public EntityDto updateEntity(EntityDto entityDto , int entityId) {
       EntityType entityType = entityRepository.findById(entityId)

                .orElseThrow(() -> new ResourceNotFoundException("Entity with Id '" + entityId + "' not found!"));


        // HANDLE DUPLICATE ENTRY EXCEPTION
   EntityType type = entityRepository.findByEntityType(entityDto.getEntityType());
//        if(existedMineral != null)
//            throw new DuplicateEntryException("Mineral with name '"+mineralDto.getMineralName()+"' already exist!");



      entityType.setEntityType(entityDto.getEntityType().toUpperCase());
       entityType.setShortName(entityDto.getShortName());


        return appUtils.entityToDto(entityRepository.save(entityType));

    }


    @Override
    public String deleteEntityById(int id) {
        entityRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Entity with id '"+id+"' not found!"));
        entityRepository.deleteById(id);
        return "Entity with id '"+id+"' deleted!";
    }


    @Override
    public HttpResponse searchEntity(String keyword, int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<EntityType> pageEntity = entityRepository.findByEntityTypeContainingIgnoreCase(keyword, pageable);

        List<EntityDto> dtos = appUtils.entitysToDtos(pageEntity.getContent());
        return HttpResponse.builder()
                .pageNumber(pageEntity.getNumber())
                .pageSize(pageEntity.getSize())
                .totalElements(pageEntity.getTotalElements())
                .totalPages(pageEntity.getTotalPages())
                .isLastPage(pageEntity.isLast())
                .data(dtos)
                .build();
    }

    public void uploadCsv(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            List<EntityType> dataList = reader.lines()
                    .skip(1) // Skip header row if needed
                    .map(line -> {
                        String[] parts = line.split(",");
                        EntityType csvData = new EntityType();

//                        csvData.setCountryId(Integer.parseInt(parts[0]));
                        csvData.setEntityType(parts[1]);
                        csvData.setShortName(parts[2]);


                        // Set other columns as needed
                        return csvData;
                    })
                    .toList();
            entityRepository.saveAll(dataList);
        }
    }

}
