package com.example.demo.dto;

import com.example.demo.entity.Paiement;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaiementDTO {
    
    private Long id;
    
    @NotNull(message = "La réservation est obligatoire")
    private Long reservationId;
    
    @NotNull(message = "Le montant est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le montant doit être supérieur à 0")
    private BigDecimal montant;
    
    @NotNull(message = "Le mode de paiement est obligatoire")
    private Paiement.ModePaiement modePaiement;
    
    private Paiement.Statut statut;
    
    private LocalDateTime datePaiement;
    
    // Informations supplémentaires
    private String reservationReference;
    private String utilisateurNom;
    private String utilisateurEmail;
    private BigDecimal montantReservation;
    
    // DTO pour la création de paiement
    @Data
    @Builder
    public static class CreateDTO {
        @NotNull
        private Long reservationId;
        
        @NotNull
        @DecimalMin("0.0")
        private BigDecimal montant;
        
        @NotNull
        private Paiement.ModePaiement modePaiement;
        
        private String numeroCarte; // Pour les paiements par carte
        private String dateExpiration;
        private String cvv;
    }
    
    // Méthodes utilitaires
    public boolean estReussi() {
        return statut == Paiement.Statut.REUSSI;
    }
    
    public boolean estEchoue() {
        return statut == Paiement.Statut.ECHOUE;
    }
    
    public boolean estEnAttente() {
        return statut == Paiement.Statut.EN_ATTENTE;
    }
}