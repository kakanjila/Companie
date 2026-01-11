package com.example.demo.service;

import com.example.demo.dto.VolDTO;
import com.example.demo.entity.Vol;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface VolService {
    
    // Opérations CRUD
    List<VolDTO> findAll();
    VolDTO findById(Long id);
    VolDTO findByNumeroVol(String numeroVol);
    VolDTO create(VolDTO volDTO);
    VolDTO update(Long id, VolDTO volDTO);
    void delete(Long id);
    
    // Recherches et filtres
    List<VolDTO> findByStatut(Vol.Statut statut);
    List<VolDTO> findByAvion(Long avionId);
    List<VolDTO> findByAeroportDepart(Long aeroportId);
    List<VolDTO> findByAeroportArrivee(Long aeroportId);
    List<VolDTO> findByAeroportDepartAndArrivee(Long departId, Long arriveeId);
    List<VolDTO> findByDateDepartBetween(LocalDateTime start, LocalDateTime end);
    List<VolDTO> findByPlacesDisponiblesGreaterThan(Integer places);
    List<VolDTO> findByPrixMax(BigDecimal maxPrix);
    List<VolDTO> searchVolsDisponibles();
    List<VolDTO> findVolsAVenir();
    List<VolDTO> rechercherVols(com.example.demo.dto.RechercheVolDTO rechercheVolDTO);
    
    // Gestion des statuts
    VolDTO changeStatut(Long id, Vol.Statut statut);
    List<VolDTO> updateVolsStatutsAutomatiquement();
    
    // Gestion des places
    VolDTO reserverPlaces(Long id, Integer nbPlaces);
    VolDTO libererPlaces(Long id, Integer nbPlaces);
    
    // Vérifications
    boolean existsByNumeroVol(String numeroVol);
    
    // Statistiques
    long countByStatut(Vol.Statut statut);
    long countVolsAvenir();
    BigDecimal getChiffreAffairePotentiel();
    
    // Validation métier
    void validateVolForReservation(Vol vol);
    boolean isVolComplet(Vol vol);
}