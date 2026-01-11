package com.example.demo.service;

import com.example.demo.dto.AvionDTO;
import com.example.demo.entity.Avion;

import java.util.List;

public interface AvionService {
    
    List<AvionDTO> findAll();
    AvionDTO findById(Long id);
    AvionDTO create(AvionDTO avionDTO);
    AvionDTO update(Long id, AvionDTO avionDTO);
    void delete(Long id);
    
    // Opérations métier
    List<AvionDTO> findDisponibles();
    List<AvionDTO> findByStatut(Avion.Statut statut);
    List<AvionDTO> findByModele(String modele);
    AvionDTO changeStatut(Long id, Avion.Statut statut);
    AvionDTO findByNumeroAvion(String numeroAvion);
    
    // Vérifications
    boolean existsByNumeroAvion(String numeroAvion);
    
    // Statistiques
    long countByStatut(Avion.Statut statut);
    Integer getCapaciteTotale();
    List<AvionDTO> findByCapaciteGreaterThanEqual(Integer capacite);
    
    // Validation métier
    void validateAvionForVol(Avion avion);
}