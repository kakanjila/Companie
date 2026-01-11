package com.example.demo.mapper;

import com.example.demo.dto.AvionDTO;
import com.example.demo.entity.Avion;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-11T09:48:28+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class AvionMapperImpl implements AvionMapper {

    @Override
    public Avion toEntity(AvionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Avion.AvionBuilder avion = Avion.builder();

        avion.id( dto.getId() );
        avion.numeroAvion( dto.getNumeroAvion() );
        avion.modele( dto.getModele() );
        avion.capacite( dto.getCapacite() );
        avion.statut( dto.getStatut() );

        return avion.build();
    }

    @Override
    public AvionDTO toDto(Avion entity) {
        if ( entity == null ) {
            return null;
        }

        AvionDTO.AvionDTOBuilder avionDTO = AvionDTO.builder();

        avionDTO.id( entity.getId() );
        avionDTO.numeroAvion( entity.getNumeroAvion() );
        avionDTO.modele( entity.getModele() );
        avionDTO.capacite( entity.getCapacite() );
        avionDTO.statut( entity.getStatut() );
        avionDTO.createdAt( entity.getCreatedAt() );

        return avionDTO.build();
    }
}
