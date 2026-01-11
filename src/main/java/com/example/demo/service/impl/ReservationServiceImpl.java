package com.example.demo.service.impl;

import com.example.demo.dto.ReservationDTO;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.Utilisateur;
import com.example.demo.entity.Vol;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.ReservationMapper;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.UtilisateurRepository;
import com.example.demo.repository.VolRepository;
import com.example.demo.repository.PaiementRepository;
import com.example.demo.entity.Paiement;
import com.example.demo.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final VolRepository volRepository;
    private final PaiementRepository paiementRepository;
    private final ReservationMapper reservationMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ReservationDTO> findAll() {
        return reservationRepository.findAll().stream()
                .map(reservationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationDTO findById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Réservation non trouvée avec l'ID : " + id));
        return reservationMapper.toDto(reservation);
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationDTO findByReference(String reference) {
        Reservation reservation = reservationRepository.findByReference(reference);
        if (reservation == null) {
            throw new ResourceNotFoundException("Réservation non trouvée avec la référence : " + reference);
        }
        return reservationMapper.toDto(reservation);
    }

    @Override
    public ReservationDTO create(ReservationDTO reservationDTO) {
        Utilisateur utilisateur = utilisateurRepository.findById(reservationDTO.getUtilisateurId())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + reservationDTO.getUtilisateurId()));

        Vol vol = volRepository.findById(reservationDTO.getVolId())
                .orElseThrow(() -> new ResourceNotFoundException("Vol non trouvé avec l'ID : " + reservationDTO.getVolId()));

        Reservation reservation = reservationMapper.toEntity(reservationDTO);
        reservation.setUtilisateur(utilisateur);
        reservation.setVol(vol);
        BigDecimal prixTotal = vol.getPrix().multiply(BigDecimal.valueOf(reservationDTO.getNbPassagers()));
        reservation.setPrixTotal(prixTotal);

        reservation.setReference(genererReferenceUnique());
        reservation.setStatut(Reservation.Statut.EN_ATTENTE);

        Reservation savedReservation = reservationRepository.save(reservation);

        Paiement paiement = new Paiement();
        paiement.setReservation(savedReservation);
        paiement.setMontant(savedReservation.getPrixTotal());
        paiement.setModePaiement(Paiement.ModePaiement.CARTE); // Valeur par défaut
        paiement.setStatut(Paiement.Statut.EN_ATTENTE);
        paiementRepository.save(paiement);

        return reservationMapper.toDto(savedReservation);
    }

    @Override
    public String genererReferenceUnique() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
    }

    // Les autres méthodes ne sont pas implémentées

    @Override
    public ReservationDTO update(Long id, ReservationDTO reservationDTO) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public void delete(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public List<ReservationDTO> findByUtilisateur(Long utilisateurId) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public List<ReservationDTO> findByVol(Long volId) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public List<ReservationDTO> findByStatut(Reservation.Statut statut) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public List<ReservationDTO> findByDateReservationBetween(LocalDateTime start, LocalDateTime end) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public List<ReservationDTO> findByUtilisateurAndVol(Long userId, Long volId) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public ReservationDTO confirmer(Long id) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public ReservationDTO annuler(Long id) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public ReservationDTO mettreEnAttente(Long id) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public boolean existsByReference(String reference) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public long countByStatut(Reservation.Statut statut) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public long countByVol(Long volId) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public BigDecimal getChiffreAffaireTotal() {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public BigDecimal getChiffreAffaireParPeriode(LocalDateTime debut, LocalDateTime fin) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public BigDecimal calculerPrixTotal(Long volId, Integer nbPassagers) {
        throw new UnsupportedOperationException("Non implémenté");
    }
}
