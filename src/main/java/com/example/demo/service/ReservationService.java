package com.example.demo.service;

import com.example.demo.dto.ReservationDTO;
import com.example.demo.entity.Reservation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService {
    
    // Opérations CRUD
    List<ReservationDTO> findAll();
    ReservationDTO findById(Long id);
    ReservationDTO findByReference(String reference);
    ReservationDTO create(ReservationDTO reservationDTO);
    ReservationDTO update(Long id, ReservationDTO reservationDTO);
    void delete(Long id);
    
    // Recherches et filtres
    List<ReservationDTO> findByUtilisateur(Long utilisateurId);
    List<ReservationDTO> findByVol(Long volId);
    List<ReservationDTO> findByStatut(Reservation.Statut statut);
    List<ReservationDTO> findByDateReservationBetween(LocalDateTime start, LocalDateTime end);
    List<ReservationDTO> findByUtilisateurAndVol(Long userId, Long volId);
    
    // Gestion des statuts
    ReservationDTO confirmer(Long id);
    ReservationDTO annuler(Long id);
    ReservationDTO mettreEnAttente(Long id);
    
    // Vérifications
    boolean existsByReference(String reference);
    
    // Statistiques
    long countByStatut(Reservation.Statut statut);
    long countByVol(Long volId);
    BigDecimal getChiffreAffaireTotal();
    BigDecimal getChiffreAffaireParPeriode(LocalDateTime debut, LocalDateTime fin);
    
    // Gestion des prix
    BigDecimal calculerPrixTotal(Long volId, Integer nbPassagers);
    
    // Génération de référence
    String genererReferenceUnique();
}