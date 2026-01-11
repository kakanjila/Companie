package com.example.demo.service;

import com.example.demo.dto.UtilisateurDTO;
import com.example.demo.entity.Utilisateur;

import java.util.List;

public interface UtilisateurService {
    
    // Opérations CRUD
    List<UtilisateurDTO> findAll();
    UtilisateurDTO findById(Long id);
    UtilisateurDTO findByEmail(String email);
    UtilisateurDTO create(UtilisateurDTO utilisateurDTO);
    UtilisateurDTO update(Long id, UtilisateurDTO utilisateurDTO);
    UtilisateurDTO updatePartial(Long id, UtilisateurDTO.UpdateDTO updateDTO);
    void delete(Long id);
    void deactivate(Long id);
    void activate(Long id);
    
    // Recherches spécifiques
    List<UtilisateurDTO> findByRole(Utilisateur.Role role);
    List<UtilisateurDTO> findByActif(Boolean actif);
    List<UtilisateurDTO> searchByNom(String nom);
    List<UtilisateurDTO> searchByPrenom(String prenom);
    
    // Vérifications
    boolean existsByEmail(String email);
    boolean isEmailUnique(Long id, String email);
    
    // Statistiques
    long countByRole(Utilisateur.Role role);
    long countActifs();
    
    // Authentification
    UtilisateurDTO authenticate(String email, String password);
    
    // Changements de rôle
    UtilisateurDTO changeRole(Long id, Utilisateur.Role newRole);
}