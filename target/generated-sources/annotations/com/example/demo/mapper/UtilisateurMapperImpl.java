package com.example.demo.mapper;

import com.example.demo.dto.UtilisateurDTO;
import com.example.demo.entity.Utilisateur;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-11T09:48:29+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class UtilisateurMapperImpl implements UtilisateurMapper {

    @Override
    public UtilisateurDTO toDto(Utilisateur entity) {
        if ( entity == null ) {
            return null;
        }

        UtilisateurDTO.UtilisateurDTOBuilder utilisateurDTO = UtilisateurDTO.builder();

        utilisateurDTO.id( entity.getId() );
        utilisateurDTO.nom( entity.getNom() );
        utilisateurDTO.prenom( entity.getPrenom() );
        utilisateurDTO.email( entity.getEmail() );
        utilisateurDTO.telephone( entity.getTelephone() );
        utilisateurDTO.role( entity.getRole() );
        utilisateurDTO.actif( entity.getActif() );

        return utilisateurDTO.build();
    }

    @Override
    public Utilisateur toEntity(UtilisateurDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Utilisateur.UtilisateurBuilder utilisateur = Utilisateur.builder();

        utilisateur.id( dto.getId() );
        utilisateur.nom( dto.getNom() );
        utilisateur.prenom( dto.getPrenom() );
        utilisateur.email( dto.getEmail() );
        utilisateur.motDePasse( dto.getMotDePasse() );
        utilisateur.telephone( dto.getTelephone() );
        utilisateur.role( dto.getRole() );
        utilisateur.actif( dto.getActif() );

        return utilisateur.build();
    }
}
