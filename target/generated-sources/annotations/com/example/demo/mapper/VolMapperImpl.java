package com.example.demo.mapper;

import com.example.demo.dto.VolDTO;
import com.example.demo.entity.Aeroport;
import com.example.demo.entity.Vol;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-11T09:48:27+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class VolMapperImpl implements VolMapper {

    @Override
    public VolDTO toDto(Vol entity) {
        if ( entity == null ) {
            return null;
        }

        VolDTO.VolDTOBuilder volDTO = VolDTO.builder();

        volDTO.avionIds( avionsToIds( entity.getAvions() ) );
        volDTO.aeroportDepartId( entityAeroportDepartId( entity ) );
        volDTO.aeroportArriveeId( entityAeroportArriveeId( entity ) );
        volDTO.id( entity.getId() );
        volDTO.numeroVol( entity.getNumeroVol() );
        volDTO.dateDepart( entity.getDateDepart() );
        volDTO.dateArrivee( entity.getDateArrivee() );
        volDTO.prix( entity.getPrix() );
        volDTO.placesDisponibles( entity.getPlacesDisponibles() );
        volDTO.statut( entity.getStatut() );
        volDTO.createdAt( entity.getCreatedAt() );

        return volDTO.build();
    }

    @Override
    public Vol toEntity(VolDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Vol.VolBuilder vol = Vol.builder();

        vol.id( dto.getId() );
        vol.numeroVol( dto.getNumeroVol() );
        vol.dateDepart( dto.getDateDepart() );
        vol.dateArrivee( dto.getDateArrivee() );
        vol.prix( dto.getPrix() );
        vol.placesDisponibles( dto.getPlacesDisponibles() );
        vol.statut( dto.getStatut() );
        vol.createdAt( dto.getCreatedAt() );

        return vol.build();
    }

    private Long entityAeroportDepartId(Vol vol) {
        if ( vol == null ) {
            return null;
        }
        Aeroport aeroportDepart = vol.getAeroportDepart();
        if ( aeroportDepart == null ) {
            return null;
        }
        Long id = aeroportDepart.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityAeroportArriveeId(Vol vol) {
        if ( vol == null ) {
            return null;
        }
        Aeroport aeroportArrivee = vol.getAeroportArrivee();
        if ( aeroportArrivee == null ) {
            return null;
        }
        Long id = aeroportArrivee.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
