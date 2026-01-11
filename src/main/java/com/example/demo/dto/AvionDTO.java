package com.example.demo.dto;

import com.example.demo.entity.Avion;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvionDTO {
    
    private Long id;
    
    @NotBlank(message = "Le numéro d'avion est obligatoire")
    private String numeroAvion;
    
    @NotBlank(message = "Le modèle est obligatoire")
    private String modele;
    
    @NotNull(message = "La capacité est obligatoire")
    @Min(value = 1, message = "La capacité doit être au moins de 1")
    private Integer capacite;
    
    private Avion.Statut statut;
    
    private LocalDateTime createdAt;
    
    // Méthodes utilitaires
    public boolean isDisponible() {
        return statut == Avion.Statut.DISPONIBLE;
    }
    
    public boolean isEnMaintenance() {
        return statut == Avion.Statut.MAINTENANCE;
    }
    
    public boolean isEnVol() {
        return statut == Avion.Statut.EN_VOL;
    }
}