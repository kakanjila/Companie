package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AeroportDTO {
    private Long id;
    private String code;
    private String nom;
    private String ville;
    private String pays;
    private String adresse;
    private String codePostal;
    private String telephone;
    private String email;
    private String siteWeb;
    private String fuseauHoraire;
    private boolean actif;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
}
