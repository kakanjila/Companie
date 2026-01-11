package com.example.demo.service;

import com.example.demo.dto.PassagerDTO;

import java.time.LocalDate;
import java.util.List;

public interface PassagerService {
    
    List<PassagerDTO> findAll();
    PassagerDTO findById(Long id);
    List<PassagerDTO> findByReservation(Long reservationId);
    PassagerDTO create(PassagerDTO passagerDTO);
    PassagerDTO update(Long id, PassagerDTO passagerDTO);
    void delete(Long id);
    void deleteByReservation(Long reservationId);
    
    List<PassagerDTO> findByNom(String nom);
    List<PassagerDTO> findByPrenom(String prenom);
    List<PassagerDTO> findByNumeroPasseport(String numeroPasseport);
    List<PassagerDTO> findByDateNaissance(LocalDate dateNaissance);
    List<PassagerDTO> findByCheckedIn(Boolean checkedIn);
    List<PassagerDTO> findByVol(Long volId);
    List<PassagerDTO> findByUtilisateur(Long utilisateurId);
    
    // Gestion du check-in
    PassagerDTO checkIn(Long id, String numeroSiege);
    PassagerDTO checkOut(Long id);
    
    // Vérifications
    boolean existsByNumeroPasseport(String numeroPasseport);
    
    // Statistiques
    long countByVol(Long volId);
    long countCheckedInByVol(Long volId);
    long countByReservation(Long reservationId);
    
    // Gestion des sièges
    List<String> getSiegesDisponibles(Long volId);
    boolean isSiegeDisponible(Long volId, String siege);
}