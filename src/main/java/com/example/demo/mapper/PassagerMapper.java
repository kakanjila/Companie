package com.example.demo.mapper;

import com.example.demo.dto.PassagerDTO;
import com.example.demo.entity.Passager;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PassagerMapper {
    
    @Mapping(source = "reservation.id", target = "reservationId")
    PassagerDTO toDto(Passager entity);
    
    @Mapping(target = "reservation", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Passager toEntity(PassagerDTO dto);
}