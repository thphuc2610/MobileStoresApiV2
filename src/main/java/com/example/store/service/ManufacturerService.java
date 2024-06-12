package com.example.store.service;

import com.example.store.dao.entity.Manufacturer;
import com.example.store.dto.request.ManufacturerRequest;
import com.example.store.dto.response.ManufacturerResponse;
import com.example.store.mapper.ManufacturerMapper;
import com.example.store.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManufacturerService {

    @Autowired
    ManufacturerRepository manufacturerRepository;

    @Autowired
    ManufacturerMapper mapper;

    public ManufacturerResponse add(ManufacturerRequest request) throws Exception {
        try {
            Manufacturer newManufacturer= manufacturerRepository.save(Manufacturer.builder()
                                                    .name(request.getName())
                                                    .build());
            return mapper.mapResult(newManufacturer);
        } catch (Exception ex) {
            throw new Exception();
        }
    }

    public List<ManufacturerResponse> getAll(){
        try {
            List<Manufacturer> list = manufacturerRepository.findAll();

            List<ManufacturerResponse> responses = new ArrayList<>();
            list.forEach(item -> {
                responses.add(mapper.mapResult(item));
            });
            return responses;
        }catch (Exception e){
            return new ArrayList<>();
        }
    }
}
