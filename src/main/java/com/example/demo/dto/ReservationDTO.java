package com.example.demo.dto;

import com.example.demo.entity.Reservation;
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
public class ReservationDTO {
    
    private Long id;
    
    @NotBlank(message = "La référence est obligatoire")
    @Size(max = 20, message = "La référence ne peut pas dépasser 20 caractères")
    private String reference;
    
    @NotNull(message = "L'utilisateur est obligatoire")
    private Long utilisateurId;
    
    @NotNull(message = "Le vol est obligatoire")
    private Long volId;
    
    @NotNull(message = "Le nombre de passagers est obligatoire")
    @Min(value = 1, message = "Le nombre de passagers doit être au moins de 1")
    private Integer nbPassagers;
    
    @NotNull(message = "Le prix total est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le prix total doit être supérieur à 0")
    private BigDecimal prixTotal;
    
    private Reservation.Statut statut;
    
    private LocalDateTime dateReservation;
    
    // Informations supplémentaires
    private String utilisateurNom;
    private String utilisateurEmail;
    private String volNumero;
    private LocalDateTime volDateDepart;
    private String villeDepart;
    private String villeArrivee;
    
    // Méthodes utilitaires
    public boolean isConfirmee() {
        return statut == Reservation.Statut.CONFIRMEE;
    }
    
    public boolean isAnnulee() {
        return statut == Reservation.Statut.ANNULEE;
    }
    
    public boolean isEnAttente() {
        return statut == Reservation.Statut.EN_ATTENTE;
    }
}