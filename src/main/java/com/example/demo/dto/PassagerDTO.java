package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassagerDTO {
    
    private Long id;
    
    @NotNull(message = "La réservation est obligatoire")
    private Long reservationId;
    
    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 100, message = "Le nom ne peut pas dépasser 100 caractères")
    private String nom;
    
    @NotBlank(message = "Le prénom est obligatoire")
    @Size(max = 100, message = "Le prénom ne peut pas dépasser 100 caractères")
    private String prenom;
    
    @NotNull(message = "La date de naissance est obligatoire")
    @Past(message = "La date de naissance doit être dans le passé")
    private LocalDate dateNaissance;
    
    @Size(max = 50, message = "Le numéro de passeport ne peut pas dépasser 50 caractères")
    private String numeroPasseport;
    
    @Size(max = 10, message = "Le numéro de siège ne peut pas dépasser 10 caractères")
    private String numeroSiege;
    
    @NotNull(message = "Le statut check-in est obligatoire")
    private Boolean checkedIn;
    
    private LocalDateTime createdAt;
    
    // Informations supplémentaires
    private String reservationReference;
    private String volNumero;
    private LocalDateTime volDateDepart;
    
    // Méthodes utilitaires
    public String getNomComplet() {
        return prenom + " " + nom;
    }
    
    public int getAge() {
        return LocalDate.now().getYear() - dateNaissance.getYear();
    }
    
    public boolean isAdulte() {
        return getAge() >= 18;
    }
    
    public boolean isEnfant() {
        return getAge() < 18 && getAge() >= 2;
    }
    
    public boolean isBebe() {
        return getAge() < 2;
    }
}