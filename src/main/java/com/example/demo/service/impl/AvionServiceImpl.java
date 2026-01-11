package com.example.demo.service.impl;

import com.example.demo.dto.AvionDTO;
import com.example.demo.entity.Avion;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.AvionMapper;
import com.example.demo.repository.AvionRepository;
import com.example.demo.service.AvionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AvionServiceImpl implements AvionService {
    
    private final AvionRepository avionRepository;
    private final AvionMapper avionMapper;
    
    @Override
    @Transactional(readOnly = true)
    public List<AvionDTO> findAll() {
        return avionRepository.findAll()
                .stream()
                .map(avionMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public AvionDTO findById(Long id) {
        Avion avion = avionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avion non trouvé avec l'ID : " + id));
        return avionMapper.toDto(avion);
    }
    
    @Override
    public AvionDTO create(AvionDTO avionDTO) {
        // Vérifier l'unicité du numéro d'avion
        if (avionRepository.existsByNumeroAvion(avionDTO.getNumeroAvion())) {
            throw new RuntimeException("Un avion avec le numéro " + avionDTO.getNumeroAvion() + " existe déjà");
        }
        
        Avion avion = avionMapper.toEntity(avionDTO);
        // S'assurer que le statut est initialisé
        if (avion.getStatut() == null) {
            avion.setStatut(Avion.Statut.DISPONIBLE);
        }
        
        Avion savedAvion = avionRepository.save(avion);
        return avionMapper.toDto(savedAvion);
    }
    
    @Override
    public AvionDTO update(Long id, AvionDTO avionDTO) {
        Avion existingAvion = avionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avion non trouvé avec l'ID : " + id));
        
        // Vérifier l'unicité du numéro d'avion (sauf pour l'avion courant)
        if (!existingAvion.getNumeroAvion().equals(avionDTO.getNumeroAvion()) 
                && avionRepository.existsByNumeroAvion(avionDTO.getNumeroAvion())) {
            throw new RuntimeException("Un autre avion avec le numéro " + avionDTO.getNumeroAvion() + " existe déjà");
        }
        
        // Mettre à jour les champs
        existingAvion.setNumeroAvion(avionDTO.getNumeroAvion());
        existingAvion.setModele(avionDTO.getModele());
        existingAvion.setCapacite(avionDTO.getCapacite());
        if (avionDTO.getStatut() != null) {
            existingAvion.setStatut(avionDTO.getStatut());
        }
        
        Avion updatedAvion = avionRepository.save(existingAvion);
        return avionMapper.toDto(updatedAvion);
    }
    
    @Override
    public void delete(Long id) {
        Avion avion = avionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avion non trouvé avec l'ID : " + id));
        
        // Vérifier si l'avion est utilisé dans des vols
        // (à implémenter si nécessaire)
        
        avionRepository.delete(avion);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AvionDTO> findDisponibles() {
        return avionRepository.findByStatut(Avion.Statut.DISPONIBLE)
                .stream()
                .map(avionMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AvionDTO> findByStatut(Avion.Statut statut) {
        return avionRepository.findByStatut(statut)
                .stream()
                .map(avionMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AvionDTO> findByModele(String modele) {
        return avionRepository.findByModele(modele)
                .stream()
                .map(avionMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public AvionDTO changeStatut(Long id, Avion.Statut statut) {
        Avion avion = avionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avion non trouvé avec l'ID : " + id));
        
        avion.setStatut(statut);
        Avion updatedAvion = avionRepository.save(avion);
        return avionMapper.toDto(updatedAvion);
    }
    
    @Override
    @Transactional(readOnly = true)
    public AvionDTO findByNumeroAvion(String numeroAvion) {
        Avion avion = avionRepository.findByNumeroAvion(numeroAvion);
        if (avion == null) {
            throw new ResourceNotFoundException("Avion non trouvé avec le numéro : " + numeroAvion);
        }
        return avionMapper.toDto(avion);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByNumeroAvion(String numeroAvion) {
        return avionRepository.existsByNumeroAvion(numeroAvion);
    }
    
    @Override
    @Transactional(readOnly = true)
    public long countByStatut(Avion.Statut statut) {
        return avionRepository.findByStatut(statut).size();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Integer getCapaciteTotale() {
        return avionRepository.findAll()
                .stream()
                .mapToInt(Avion::getCapacite)
                .sum();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AvionDTO> findByCapaciteGreaterThanEqual(Integer capacite) {
        return avionRepository.findByCapaciteGreaterThanEqual(capacite)
                .stream()
                .map(avionMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public void validateAvionForVol(Avion avion) {
        if (avion == null) {
            throw new RuntimeException("L'avion est null");
        }
        
        if (avion.getStatut() != Avion.Statut.DISPONIBLE) {
            throw new RuntimeException("L'avion " + avion.getNumeroAvion() + " n'est pas disponible. Statut : " + avion.getStatut());
        }
        
        if (avion.getCapacite() <= 0) {
            throw new RuntimeException("L'avion " + avion.getNumeroAvion() + " a une capacité invalide : " + avion.getCapacite());
        }
    }
}