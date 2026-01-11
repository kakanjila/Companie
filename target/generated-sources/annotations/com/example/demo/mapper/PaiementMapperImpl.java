package com.example.demo.mapper;

import com.example.demo.dto.PaiementDTO;
import com.example.demo.entity.Paiement;
import com.example.demo.entity.Reservation;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-09T12:26:40+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class PaiementMapperImpl implements PaiementMapper {

    @Override
    public PaiementDTO toDto(Paiement entity) {
        if ( entity == null ) {
            return null;
        }

        PaiementDTO.PaiementDTOBuilder paiementDTO = PaiementDTO.builder();

        paiementDTO.reservationId( entityReservationId( entity ) );
        paiementDTO.id( entity.getId() );
        paiementDTO.montant( entity.getMontant() );
        paiementDTO.modePaiement( entity.getModePaiement() );
        paiementDTO.statut( entity.getStatut() );
        paiementDTO.datePaiement( entity.getDatePaiement() );

        return paiementDTO.build();
    }

    @Override
    public Paiement toEntity(PaiementDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Paiement.PaiementBuilder paiement = Paiement.builder();

        paiement.id( dto.getId() );
        paiement.montant( dto.getMontant() );
        paiement.modePaiement( dto.getModePaiement() );
        paiement.statut( dto.getStatut() );

        return paiement.build();
    }

    private Long entityReservationId(Paiement paiement) {
        if ( paiement == null ) {
            return null;
        }
        Reservation reservation = paiement.getReservation();
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
