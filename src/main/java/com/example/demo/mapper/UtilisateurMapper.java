package com.example.demo.mapper;

import com.example.demo.dto.UtilisateurDTO;
import com.example.demo.entity.Utilisateur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UtilisateurMapper {
    
    @Mapping(target = "motDePasse", ignore = true) // Ne pas exposer le mot de passe
    @Mapping(target = "createdAt", ignore = true)
    UtilisateurDTO toDto(Utilisateur entity);
    
    @Mapping(target = "createdAt", ignore = true)
    Utilisateur toEntity(UtilisateurDTO dto);
}