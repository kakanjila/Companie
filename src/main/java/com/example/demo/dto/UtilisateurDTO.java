package com.example.demo.dto;

import com.example.demo.entity.Utilisateur;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtilisateurDTO {
    
    private Long id;
    
    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 100, message = "Le nom ne peut pas dépasser 100 caractères")
    private String nom;
    
    @NotBlank(message = "Le prénom est obligatoire")
    @Size(max = 100, message = "Le prénom ne peut pas dépasser 100 caractères")
    private String prenom;
    
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    @Size(max = 150, message = "L'email ne peut pas dépasser 150 caractères")
    private String email;
    
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String motDePasse;
    
    @Size(max = 20, message = "Le téléphone ne peut pas dépasser 20 caractères")
    private String telephone;
    
    @NotNull(message = "Le rôle est obligatoire")
    private Utilisateur.Role role;
    
    @NotNull(message = "Le statut actif est obligatoire")
    private Boolean actif;
    
    private LocalDateTime createdAt;
    
    // Méthodes utilitaires
    public boolean isAdmin() {
        return role == Utilisateur.Role.ADMIN;
    }
    
    public boolean isAgent() {
        return role == Utilisateur.Role.AGENT;
    }
    
    public boolean isClient() {
        return role == Utilisateur.Role.CLIENT;
    }
    
    // DTO pour les mises à jour (sans mot de passe)
    @Data
    @Builder
    public static class UpdateDTO {
        private String nom;
        private String prenom;
        private String telephone;
        private Boolean actif;
    }
}