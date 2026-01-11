package com.example.demo.service;

import com.example.demo.dto.PaiementDTO;
import com.example.demo.entity.Paiement;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface PaiementService {
    
    List<PaiementDTO> findAll();
    PaiementDTO findById(Long id);
    List<PaiementDTO> findByReservation(Long reservationId);
    PaiementDTO create(PaiementDTO.CreateDTO paiementDTO);
    PaiementDTO update(Long id, PaiementDTO paiementDTO);
    void delete(Long id);
    
    // Recherches et filtres
    List<PaiementDTO> findByStatut(Paiement.Statut statut);
    List<PaiementDTO> findByModePaiement(Paiement.ModePaiement modePaiement);
    List<PaiementDTO> findByDatePaiementBetween(LocalDateTime start, LocalDateTime end);
    List<PaiementDTO> findByUtilisateur(Long utilisateurId);
    List<PaiementDTO> findByMontantGreaterThanEqual(BigDecimal montantMin);
    List<PaiementDTO> findByMontantLessThanEqual(BigDecimal montantMax);
    
    // Gestion des statuts
    PaiementDTO confirmerPaiement(Long id);
    PaiementDTO echouerPaiement(Long id, String raison);
    PaiementDTO mettreEnAttente(Long id);
    
    // Traitement des paiements
    PaiementDTO traiterPaiementCarte(PaiementDTO.CreateDTO paiementDTO);
    PaiementDTO traiterPaiementPaypal(PaiementDTO.CreateDTO paiementDTO);
    PaiementDTO traiterPaiementEspeces(PaiementDTO.CreateDTO paiementDTO);
    
    // Vérifications
    boolean isReservationPayee(Long reservationId);
    
    // Statistiques
    long countByStatut(Paiement.Statut statut);
    BigDecimal getTotalPaiementsReussis();
    BigDecimal getTotalPaiementsParPeriode(LocalDateTime debut, LocalDateTime fin);
    BigDecimal getMoyennePaiements();
    
    // Rapports
    List<Object[]> getPaiementsParMode();
    List<Object[]> getPaiementsParMois(int annee);
    
    // Remboursements
    PaiementDTO effectuerRemboursement(Long paiementId, BigDecimal montant);
}