package com.example.demo.mapper;

import com.example.demo.dto.PaiementDTO;
import com.example.demo.entity.Paiement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaiementMapper {
    
    @Mapping(source = "reservation.id", target = "reservationId")
    PaiementDTO toDto(Paiement entity);
    
    @Mapping(target = "reservation", ignore = true)
    @Mapping(target = "datePaiement", ignore = true)
    Paiement toEntity(PaiementDTO dto);
}