package com.example.store.mapper;

import com.example.store.dao.entity.Manufacturer;
import com.example.store.dto.response.ManufacturerResponse;
import org.springframework.stereotype.Component;

@Component
public class ManufacturerMapper {

    public ManufacturerResponse mapResult(Manufacturer manufacturer){
        return ManufacturerResponse.builder()
                .id(manufacturer.getId())
                .name(manufacturer.getName())
                .build();
    }
}
