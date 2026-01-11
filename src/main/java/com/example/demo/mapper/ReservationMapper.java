package com.example.demo.mapper;

import com.example.demo.dto.ReservationDTO;
import com.example.demo.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    
    @Mapping(source = "utilisateur.id", target = "utilisateurId")
    @Mapping(source = "vol.id", target = "volId")
    ReservationDTO toDto(Reservation entity);
    
    @Mapping(target = "utilisateur", ignore = true)
    @Mapping(target = "vol", ignore = true)
    @Mapping(target = "dateReservation", ignore = true)
    Reservation toEntity(ReservationDTO dto);
}