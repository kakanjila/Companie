package com.example.demo.mapper;

import com.example.demo.dto.AeroportDTO;
import com.example.demo.entity.Aeroport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", 
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AeroportMapper {
    
    AeroportMapper INSTANCE = Mappers.getMapper(AeroportMapper.class);
    
    AeroportDTO toDto(Aeroport aeroport);
    
    Aeroport toEntity(AeroportDTO aeroportDTO);
    
    void updateAeroportFromDto(AeroportDTO aeroportDTO, @MappingTarget Aeroport aeroport);
}
