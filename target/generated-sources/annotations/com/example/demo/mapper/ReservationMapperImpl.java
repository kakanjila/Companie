package com.example.demo.mapper;

import com.example.demo.dto.ReservationDTO;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.Utilisateur;
import com.example.demo.entity.Vol;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-09T12:26:40+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class ReservationMapperImpl implements ReservationMapper {

    @Override
    public ReservationDTO toDto(Reservation entity) {
        if ( entity == null ) {
            return null;
        }

        ReservationDTO.ReservationDTOBuilder reservationDTO = ReservationDTO.builder();

        reservationDTO.utilisateurId( entityUtilisateurId( entity ) );
        reservationDTO.volId( entityVolId( entity ) );
        reservationDTO.id( entity.getId() );
        reservationDTO.reference( entity.getReference() );
        reservationDTO.nbPassagers( entity.getNbPassagers() );
        reservationDTO.prixTotal( entity.getPrixTotal() );
        reservationDTO.statut( entity.getStatut() );
        reservationDTO.dateReservation( entity.getDateReservation() );

        return reservationDTO.build();
    }

    @Override
    public Reservation toEntity(ReservationDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Reservation.ReservationBuilder reservation = Reservation.builder();

        reservation.id( dto.getId() );
        reservation.reference( dto.getReference() );
        reservation.nbPassagers( dto.getNbPassagers() );
        reservation.prixTotal( dto.getPrixTotal() );
        reservation.statut( dto.getStatut() );

        return reservation.build();
    }

    private Long entityUtilisateurId(Reservation reservation) {
        if ( reservation == null ) {
            return null;
        }
        Utilisateur utilisateur = reservation.getUtilisateur();
        if ( utilisateur == null ) {
            return null;
        }
        Long id = utilisateur.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityVolId(Reservation reservation) {
        if ( reservation == null ) {
            return null;
        }
        Vol vol = reservation.getVol();
        if ( vol == null ) {
            return null;
        }
        Long id = vol.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
