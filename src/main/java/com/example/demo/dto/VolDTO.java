package com.example.demo.dto;

import com.example.demo.entity.Vol;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VolDTO {
    
    private Long id;
    
    @NotBlank(message = "Le numéro de vol est obligatoire")
    @Size(max = 10, message = "Le numéro de vol ne peut pas dépasser 10 caractères")
    private String numeroVol;
    
    @NotEmpty(message = "Au moins un avion est requis")
    private List<Long> avionIds;
    
    @NotNull(message = "L'aéroport de départ est obligatoire")
    private Long aeroportDepartId;
    
    @NotNull(message = "L'aéroport d'arrivée est obligatoire")
    private Long aeroportArriveeId;
    
    @NotNull(message = "La date de départ est obligatoire")
    @Future(message = "La date de départ doit être dans le futur")
    private LocalDateTime dateDepart;
    
    @NotNull(message = "La date d'arrivée est obligatoire")
    @Future(message = "La date d'arrivée doit être dans le futur")
    private LocalDateTime dateArrivee;
    
    @NotNull(message = "Le prix est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit être supérieur à 0")
    private BigDecimal prix;
    
    @NotNull(message = "Le nombre de places disponibles est obligatoire")
    @Min(value = 0, message = "Le nombre de places disponibles ne peut pas être négatif")
    private Integer placesDisponibles;
    
    private Vol.Statut statut;
    
    private LocalDateTime createdAt;
    
    // Informations supplémentaires (pour les réponses)
    private List<String> avionModeles;
    private String aeroportDepartCode;
    private String aeroportArriveeCode;
    private String villeDepart;
    private String villeArrivee;
    
    // Méthodes utilitaires
    public boolean isDisponible() {
        return placesDisponibles > 0 && statut == Vol.Statut.PREVU;
    }
    
    public boolean estTermine() {
        return LocalDateTime.now().isAfter(dateArrivee);
    }
    
    public boolean estEnCours() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(dateDepart) && now.isBefore(dateArrivee);
    }
}