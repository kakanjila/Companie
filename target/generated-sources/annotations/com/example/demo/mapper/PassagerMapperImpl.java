package com.example.demo.mapper;

import com.example.demo.dto.PassagerDTO;
import com.example.demo.entity.Passager;
import com.example.demo.entity.Reservation;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-09T12:26:38+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class PassagerMapperImpl implements PassagerMapper {

    @Override
    public PassagerDTO toDto(Passager entity) {
        if ( entity == null ) {
            return null;
        }

        PassagerDTO.PassagerDTOBuilder passagerDTO = PassagerDTO.builder();

        passagerDTO.reservationId( entityReservationId( entity ) );
        passagerDTO.id( entity.getId() );
        passagerDTO.nom( entity.getNom() );
        passagerDTO.prenom( entity.getPrenom() );
        passagerDTO.dateNaissance( entity.getDateNaissance() );
        passagerDTO.numeroPasseport( entity.getNumeroPasseport() );
        passagerDTO.numeroSiege( entity.getNumeroSiege() );
        passagerDTO.checkedIn( entity.getCheckedIn() );
        passagerDTO.createdAt( entity.getCreatedAt() );

        return passagerDTO.build();
    }

    @Override
    public Passager toEntity(PassagerDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Passager.PassagerBuilder passager = Passager.builder();

        passager.id( dto.getId() );
        passager.nom( dto.getNom() );
        passager.prenom( dto.getPrenom() );
        passager.dateNaissance( dto.getDateNaissance() );
        passager.numeroPasseport( dto.getNumeroPasseport() );
        passager.numeroSiege( dto.getNumeroSiege() );
        passager.checkedIn( dto.getCheckedIn() );

        return passager.build();
    }

    private Long entityReservationId(Passager passager) {
        if ( passager == null ) {
            return null;
        }
        Reservation reservation = passager.getReservation();
        if ( reservation == null ) {
            return null;
        }
        Long id = reservation.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
