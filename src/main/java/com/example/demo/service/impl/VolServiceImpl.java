package com.example.demo.service.impl;

import com.example.demo.dto.VolDTO;
import com.example.demo.entity.Aeroport;
import com.example.demo.entity.Avion;
import com.example.demo.entity.Vol;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.VolMapper;
import com.example.demo.repository.AeroportRepository;
import com.example.demo.repository.AvionRepository;
import com.example.demo.repository.VolRepository;
import com.example.demo.service.VolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class VolServiceImpl implements VolService {

    private final VolRepository volRepository;
    private final AvionRepository avionRepository;
    private final AeroportRepository aeroportRepository;
    private final VolMapper volMapper;

    @Override
    @Transactional(readOnly = true)
    public List<VolDTO> findAll() {
        return volRepository.findAll().stream()
                .map(this::enrichVolDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public VolDTO findById(Long id) {
        Vol vol = volRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vol non trouvé avec l'ID : " + id));
        return enrichVolDTO(vol);
    }

    @Override
    @Transactional(readOnly = true)
    public VolDTO findByNumeroVol(String numeroVol) {
        Vol vol = volRepository.findByNumeroVol(numeroVol);
        if (vol == null) {
            throw new ResourceNotFoundException("Vol non trouvé avec le numéro : " + numeroVol);
        }
        return enrichVolDTO(vol);
    }

    @Override
    public VolDTO create(VolDTO volDTO) {
        if (volRepository.existsByNumeroVol(volDTO.getNumeroVol())) {
            throw new RuntimeException("Un vol avec le numéro " + volDTO.getNumeroVol() + " existe déjà");
        }

        Set<Avion> avions = volDTO.getAvionIds().stream()
                .map(avionId -> avionRepository.findById(avionId)
                        .orElseThrow(() -> new ResourceNotFoundException("Avion non trouvé avec l'ID : " + avionId)))
                .collect(Collectors.toSet());

        Aeroport aeroportDepart = aeroportRepository.findById(volDTO.getAeroportDepartId())
                .orElseThrow(() -> new ResourceNotFoundException("Aéroport de départ non trouvé avec l'ID : " + volDTO.getAeroportDepartId()));

        Aeroport aeroportArrivee = aeroportRepository.findById(volDTO.getAeroportArriveeId())
                .orElseThrow(() -> new ResourceNotFoundException("Aéroport d'arrivée non trouvé avec l'ID : " + volDTO.getAeroportArriveeId()));

        if (aeroportDepart.getId().equals(aeroportArrivee.getId())) {
            throw new RuntimeException("L'aéroport de départ et d'arrivée doivent être différents");
        }

        if (!volDTO.getDateArrivee().isAfter(volDTO.getDateDepart())) {
            throw new RuntimeException("La date d'arrivée doit être après la date de départ");
        }

        int capaciteTotale = avions.stream().mapToInt(Avion::getCapacite).sum();
        if (volDTO.getPlacesDisponibles() > capaciteTotale) {
            throw new RuntimeException("Le nombre de places disponibles ne peut pas dépasser la capacité totale des avions (" + capaciteTotale + ")");
        }

        Vol vol = volMapper.toEntity(volDTO);
        vol.setAvions(avions);
        vol.setAeroportDepart(aeroportDepart);
        vol.setAeroportArrivee(aeroportArrivee);

        if (vol.getStatut() == null) {
            vol.setStatut(Vol.Statut.PREVU);
        }

        avions.forEach(avion -> {
            avion.setStatut(Avion.Statut.EN_VOL);
            avionRepository.save(avion);
        });

        Vol savedVol = volRepository.save(vol);
        return enrichVolDTO(savedVol);
    }

    @Override
    public VolDTO update(Long id, VolDTO volDTO) {
        Vol existingVol = volRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vol non trouvé avec l'ID : " + id));

        if (!existingVol.getNumeroVol().equals(volDTO.getNumeroVol()) && volRepository.existsByNumeroVol(volDTO.getNumeroVol())) {
            throw new RuntimeException("Un autre vol avec le numéro " + volDTO.getNumeroVol() + " existe déjà");
        }

        Set<Avion> avions = volDTO.getAvionIds().stream()
                .map(avionId -> avionRepository.findById(avionId)
                        .orElseThrow(() -> new ResourceNotFoundException("Avion non trouvé avec l'ID : " + avionId)))
                .collect(Collectors.toSet());

        Aeroport aeroportDepart = existingVol.getAeroportDepart();
        if (!aeroportDepart.getId().equals(volDTO.getAeroportDepartId())) {
            aeroportDepart = aeroportRepository.findById(volDTO.getAeroportDepartId())
                    .orElseThrow(() -> new ResourceNotFoundException("Aéroport de départ non trouvé avec l'ID : " + volDTO.getAeroportDepartId()));
        }

        Aeroport aeroportArrivee = existingVol.getAeroportArrivee();
        if (!aeroportArrivee.getId().equals(volDTO.getAeroportArriveeId())) {
            aeroportArrivee = aeroportRepository.findById(volDTO.getAeroportArriveeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Aéroport d'arrivée non trouvé avec l'ID : " + volDTO.getAeroportArriveeId()));
        }

        existingVol.setNumeroVol(volDTO.getNumeroVol());
        existingVol.setAvions(avions);
        existingVol.setAeroportDepart(aeroportDepart);
        existingVol.setAeroportArrivee(aeroportArrivee);
        existingVol.setDateDepart(volDTO.getDateDepart());
        existingVol.setDateArrivee(volDTO.getDateArrivee());
        existingVol.setPrix(volDTO.getPrix());
        existingVol.setPlacesDisponibles(volDTO.getPlacesDisponibles());
        if (volDTO.getStatut() != null) {
            existingVol.setStatut(volDTO.getStatut());
        }

        Vol updatedVol = volRepository.save(existingVol);
        return enrichVolDTO(updatedVol);
    }

    @Override
    public void delete(Long id) {
        Vol vol = volRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vol non trouvé avec l'ID : " + id));
        volRepository.delete(vol);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VolDTO> findByStatut(Vol.Statut statut) {
        return volRepository.findByStatut(statut).stream()
                .map(this::enrichVolDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VolDTO> findByAvion(Long avionId) {
        return volRepository.findByAvionId(avionId).stream()
                .map(this::enrichVolDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VolDTO> findByAeroportDepart(Long aeroportId) {
        return volRepository.findByAeroportDepartId(aeroportId).stream()
                .map(this::enrichVolDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VolDTO> findByAeroportArrivee(Long aeroportId) {
        return volRepository.findByAeroportArriveeId(aeroportId).stream()
                .map(this::enrichVolDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VolDTO> findByAeroportDepartAndArrivee(Long departId, Long arriveeId) {
        return volRepository.findByAeroportDepartAndArrivee(departId, arriveeId).stream()
                .map(this::enrichVolDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VolDTO> findByDateDepartBetween(LocalDateTime start, LocalDateTime end) {
        return volRepository.findByDateDepartBetween(start, end).stream()
                .map(this::enrichVolDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VolDTO> findByPlacesDisponiblesGreaterThan(Integer places) {
        return volRepository.findByPlacesDisponiblesGreaterThan(places).stream()
                .map(this::enrichVolDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VolDTO> findByPrixMax(BigDecimal maxPrix) {
        return volRepository.findByPrixLessThanEqual(maxPrix).stream()
                .map(this::enrichVolDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VolDTO> searchVolsDisponibles() {
        return volRepository.findByStatutAndPlacesDisponiblesGreaterThan(Vol.Statut.PREVU, 0).stream()
                .map(this::enrichVolDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VolDTO changeStatut(Long id, Vol.Statut statut) {
        Vol vol = volRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vol non trouvé avec l'ID : " + id));
        vol.setStatut(statut);
        return enrichVolDTO(volRepository.save(vol));
    }

    @Override
    public List<VolDTO> updateVolsStatutsAutomatiquement() {
        LocalDateTime now = LocalDateTime.now();
        List<Vol> volsAUpdater = volRepository.findByStatut(Vol.Statut.PREVU).stream()
                .filter(v -> v.getDateDepart().isBefore(now))
                .collect(Collectors.toList());

        volsAUpdater.forEach(vol -> {
            if (now.isAfter(vol.getDateArrivee())) {
                vol.setStatut(Vol.Statut.ATTERRI);
            } else {
                vol.setStatut(Vol.Statut.EN_VOL);
            }
        });

        return volRepository.saveAll(volsAUpdater).stream()
                .map(this::enrichVolDTO)
                .collect(Collectors.toList());
    }

    

    

    @Override
    @Transactional(readOnly = true)
    public boolean existsByNumeroVol(String numeroVol) {
        return volRepository.existsByNumeroVol(numeroVol);
    }

    @Override
    @Transactional(readOnly = true)
    public long countVolsAvenir() {
        return volRepository.findByDateDepartBetween(LocalDateTime.now(), LocalDateTime.MAX).size();
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getChiffreAffairePotentiel() {
        return volRepository.findAll().stream()
                .map(vol -> vol.getPrix().multiply(BigDecimal.valueOf(vol.getAvions().stream().mapToInt(Avion::getCapacite).sum())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public void validateVolForReservation(Vol vol) {
        if (vol.getStatut() != Vol.Statut.PREVU) {
            throw new RuntimeException("Le vol n'est pas prévu.");
        }
        if (isVolComplet(vol)) {
            throw new RuntimeException("Le vol est complet.");
        }
    }

        

        @Override
        public VolDTO reserverPlaces(Long id, Integer nbPlaces) {
            Vol vol = volRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Vol non trouvé avec l'ID : " + id));

            if (vol.getPlacesDisponibles() < nbPlaces) {
                throw new RuntimeException("Pas assez de places disponibles.");
            }

            vol.setPlacesDisponibles(vol.getPlacesDisponibles() - nbPlaces);
            return enrichVolDTO(volRepository.save(vol));
        }

        @Override
        public VolDTO libererPlaces(Long id, Integer nbPlaces) {
            Vol vol = volRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Vol non trouvé avec l'ID : " + id));

            vol.setPlacesDisponibles(vol.getPlacesDisponibles() + nbPlaces);
            return enrichVolDTO(volRepository.save(vol));
        }

        

        @Override
        @Transactional(readOnly = true)
        public long countByStatut(Vol.Statut statut) {
            return volRepository.findByStatut(statut).size();
        }


        @Override
        public boolean isVolComplet(Vol vol) {
            return vol.getPlacesDisponibles() <= 0;
        }

        private VolDTO enrichVolDTO(Vol vol) {
            VolDTO dto = volMapper.toDto(vol);
            dto.setAvionModeles(vol.getAvions().stream().map(Avion::getModele).collect(Collectors.toList()));
            dto.setAeroportDepartCode(vol.getAeroportDepart().getCode());
            dto.setAeroportArriveeCode(vol.getAeroportArrivee().getCode());
            dto.setVilleDepart(vol.getAeroportDepart().getVille());
            dto.setVilleArrivee(vol.getAeroportArrivee().getVille());
            return dto;
        }

        @Override
        public List<VolDTO> findVolsAVenir() {
            return volRepository.findByDateDepartBetween(LocalDateTime.now(), LocalDateTime.MAX).stream()
                    .map(this::enrichVolDTO)
                    .collect(Collectors.toList());
        }

        @Override
        public List<VolDTO> rechercherVols(com.example.demo.dto.RechercheVolDTO rechercheVolDTO) {
            return volRepository.findByVilleDepartAndVilleArriveeAndDateDepart(
                rechercheVolDTO.getVilleDepart(),
                rechercheVolDTO.getVilleArrivee(),
                rechercheVolDTO.getDateDepart().atStartOfDay()
            ).stream()
            .map(this::enrichVolDTO)
            .collect(Collectors.toList());
        }
    }