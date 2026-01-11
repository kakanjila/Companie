package com.example.demo.mapper;

import com.example.demo.dto.AvionDTO;
import com.example.demo.entity.Avion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AvionMapper {
    
    @Mapping(target = "createdAt", ignore = true)
    Avion toEntity(AvionDTO dto);
    
    AvionDTO toDto(Avion entity);
}