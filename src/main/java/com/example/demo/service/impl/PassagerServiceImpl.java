package com.example.demo.service.impl;

import com.example.demo.dto.PassagerDTO;
import com.example.demo.entity.Passager;
import com.example.demo.entity.Reservation;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.PassagerMapper;
import com.example.demo.repository.PassagerRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.service.PassagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PassagerServiceImpl implements PassagerService {

    private final PassagerRepository passagerRepository;
    private final ReservationRepository reservationRepository;
    private final PassagerMapper passagerMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PassagerDTO> findAll() {
        return passagerRepository.findAll().stream()
                .map(passagerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PassagerDTO findById(Long id) {
        Passager passager = passagerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passager non trouvé avec l'ID : " + id));
        return passagerMapper.toDto(passager);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PassagerDTO> findByReservation(Long reservationId) {
        return passagerRepository.findByReservationId(reservationId).stream()
                .map(passagerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PassagerDTO create(PassagerDTO passagerDTO) {
        Reservation reservation = reservationRepository.findById(passagerDTO.getReservationId())
                .orElseThrow(() -> new ResourceNotFoundException("Réservation non trouvée avec l'ID : " + passagerDTO.getReservationId()));

        Passager passager = passagerMapper.toEntity(passagerDTO);
        passager.setReservation(reservation);

        return passagerMapper.toDto(passagerRepository.save(passager));
    }

    // Les autres méthodes ne sont pas implémentées

    @Override
    public PassagerDTO update(Long id, PassagerDTO passagerDTO) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public void delete(Long id) {
        passagerRepository.deleteById(id);
    }

    @Override
    public void deleteByReservation(Long reservationId) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public List<PassagerDTO> findByNom(String nom) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public List<PassagerDTO> findByPrenom(String prenom) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public List<PassagerDTO> findByNumeroPasseport(String numeroPasseport) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public List<PassagerDTO> findByDateNaissance(LocalDate dateNaissance) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public List<PassagerDTO> findByCheckedIn(Boolean checkedIn) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public List<PassagerDTO> findByVol(Long volId) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public List<PassagerDTO> findByUtilisateur(Long utilisateurId) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public PassagerDTO checkIn(Long id, String numeroSiege) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public PassagerDTO checkOut(Long id) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public boolean existsByNumeroPasseport(String numeroPasseport) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public long countByVol(Long volId) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public long countCheckedInByVol(Long volId) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public long countByReservation(Long reservationId) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public List<String> getSiegesDisponibles(Long volId) {
        throw new UnsupportedOperationException("Non implémenté");
    }

    @Override
    public boolean isSiegeDisponible(Long volId, String siege) {
        throw new UnsupportedOperationException("Non implémenté");
    }
}
