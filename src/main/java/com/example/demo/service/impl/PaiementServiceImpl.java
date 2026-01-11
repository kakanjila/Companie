package com.example.demo.service.impl;

import com.example.demo.dto.PaiementDTO;
import com.example.demo.entity.Paiement;
import com.example.demo.entity.Reservation;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.PaiementMapper;
import com.example.demo.repository.PaiementRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.service.PaiementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PaiementServiceImpl implements PaiementService {

    private final PaiementRepository paiementRepository;
    private final ReservationRepository reservationRepository;
    private final PaiementMapper paiementMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PaiementDTO> findAll() {
        return paiementRepository.findAll().stream()
                .map(paiementMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PaiementDTO findById(Long id) {
        Paiement paiement = paiementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement non trouvé avec l'ID : " + id));
        return paiementMapper.toDto(paiement);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaiementDTO> findByReservation(Long reservationId) {
        return paiementRepository.findByReservationId(reservationId).stream()
                .map(paiementMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PaiementDTO create(PaiementDTO.CreateDTO createDTO) {
        Reservation reservation = reservationRepository.findById(createDTO.getReservationId())
                .orElseThrow(() -> new ResourceNotFoundException("Réservation non trouvée avec l'ID : " + createDTO.getReservationId()));

        Paiement paiement = new Paiement();
        paiement.setReservation(reservation);
        paiement.setMontant(createDTO.getMontant());
        paiement.setModePaiement(createDTO.getModePaiement());
        paiement.setStatut(Paiement.Statut.REUSSI); // Simplifié pour l'exemple

        return paiementMapper.toDto(paiementRepository.save(paiement));
    }

    // Les autres méthodes ne sont pas implémentées pour garder l'exemple concis

    @Override
    public PaiementDTO update(Long id, PaiementDTO paiementDTO) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public void delete(Long id) {
        paiementRepository.deleteById(id);
    }

    @Override
    public List<PaiementDTO> findByStatut(Paiement.Statut statut) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public List<PaiementDTO> findByModePaiement(Paiement.ModePaiement modePaiement) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public List<PaiementDTO> findByDatePaiementBetween(LocalDateTime start, LocalDateTime end) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public List<PaiementDTO> findByUtilisateur(Long utilisateurId) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public List<PaiementDTO> findByMontantGreaterThanEqual(BigDecimal montantMin) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public List<PaiementDTO> findByMontantLessThanEqual(BigDecimal montantMax) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public PaiementDTO confirmerPaiement(Long id) {
        Paiement paiement = paiementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement non trouvé avec l'ID : " + id));

        paiement.setStatut(Paiement.Statut.REUSSI);
        Paiement savedPaiement = paiementRepository.save(paiement);

        Reservation reservation = savedPaiement.getReservation();
        reservation.setStatut(Reservation.Statut.CONFIRMEE);
        reservationRepository.save(reservation);

        return paiementMapper.toDto(savedPaiement);
    }

    @Override
    public PaiementDTO echouerPaiement(Long id, String raison) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public PaiementDTO mettreEnAttente(Long id) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public PaiementDTO traiterPaiementCarte(PaiementDTO.CreateDTO paiementDTO) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public PaiementDTO traiterPaiementPaypal(PaiementDTO.CreateDTO paiementDTO) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public PaiementDTO traiterPaiementEspeces(PaiementDTO.CreateDTO paiementDTO) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public boolean isReservationPayee(Long reservationId) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public long countByStatut(Paiement.Statut statut) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public BigDecimal getTotalPaiementsReussis() {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public BigDecimal getTotalPaiementsParPeriode(LocalDateTime debut, LocalDateTime fin) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public BigDecimal getMoyennePaiements() {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public List<Object[]> getPaiementsParMode() {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public List<Object[]> getPaiementsParMois(int annee) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public PaiementDTO effectuerRemboursement(Long paiementId, BigDecimal montant) {
        throw new UnsupportedOperationException("Non implémenté");
    }
}
