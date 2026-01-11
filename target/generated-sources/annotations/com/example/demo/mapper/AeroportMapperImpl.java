package com.example.demo.mapper;

import com.example.demo.dto.AeroportDTO;
import com.example.demo.entity.Aeroport;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-09T12:26:39+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class AeroportMapperImpl implements AeroportMapper {

    @Override
    public AeroportDTO toDto(Aeroport aeroport) {
        if ( aeroport == null ) {
            return null;
        }

        AeroportDTO.AeroportDTOBuilder aeroportDTO = AeroportDTO.builder();

        aeroportDTO.id( aeroport.getId() );
        aeroportDTO.code( aeroport.getCode() );
        aeroportDTO.nom( aeroport.getNom() );
        aeroportDTO.ville( aeroport.getVille() );
        aeroportDTO.pays( aeroport.getPays() );
        if ( aeroport.getActif() != null ) {
            aeroportDTO.actif( aeroport.getActif() );
        }

        return aeroportDTO.build();
    }

    @Override
    public Aeroport toEntity(AeroportDTO aeroportDTO) {
        if ( aeroportDTO == null ) {
            return null;
        }

        Aeroport.AeroportBuilder aeroport = Aeroport.builder();

        aeroport.id( aeroportDTO.getId() );
        aeroport.code( aeroportDTO.getCode() );
        aeroport.nom( aeroportDTO.getNom() );
        aeroport.ville( aeroportDTO.getVille() );
        aeroport.pays( aeroportDTO.getPays() );
        aeroport.actif( aeroportDTO.isActif() );

        return aeroport.build();
    }

    @Override
    public void updateAeroportFromDto(AeroportDTO aeroportDTO, Aeroport aeroport) {
        if ( aeroportDTO == null ) {
            return;
        }

        if ( aeroportDTO.getId() != null ) {
            aeroport.setId( aeroportDTO.getId() );
        }
        if ( aeroportDTO.getCode() != null ) {
            aeroport.setCode( aeroportDTO.getCode() );
        }
        if ( aeroportDTO.getNom() != null ) {
            aeroport.setNom( aeroportDTO.getNom() );
        }
        if ( aeroportDTO.getVille() != null ) {
            aeroport.setVille( aeroportDTO.getVille() );
        }
        if ( aeroportDTO.getPays() != null ) {
            aeroport.setPays( aeroportDTO.getPays() );
        }
        aeroport.setActif( aeroportDTO.isActif() );
    }
}
