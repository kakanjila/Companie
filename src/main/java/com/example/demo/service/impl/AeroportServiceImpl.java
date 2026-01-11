package com.example.demo.service.impl;

import com.example.demo.dto.AeroportDTO;
import com.example.demo.entity.Aeroport;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.AeroportMapper;
import com.example.demo.repository.AeroportRepository;
import com.example.demo.service.AeroportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AeroportServiceImpl implements AeroportService {

    private final AeroportRepository aeroportRepository;
    private final AeroportMapper aeroportMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AeroportDTO> findAll() {
        return aeroportRepository.findAll().stream()
                .map(aeroportMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AeroportDTO findById(Long id) {
        Aeroport aeroport = aeroportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aéroport non trouvé avec l'id : " + id));
        return aeroportMapper.toDto(aeroport);
    }

    @Override
    @Transactional(readOnly = true)
    public AeroportDTO findByCode(String code) {
        Aeroport aeroport = aeroportRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Aéroport non trouvé avec le code : " + code));
        return aeroportMapper.toDto(aeroport);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AeroportDTO> findActifs() {
        return aeroportRepository.findByActifTrue().stream()
                .map(aeroportMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AeroportDTO save(AeroportDTO aeroportDTO) {
        Aeroport aeroport = aeroportMapper.toEntity(aeroportDTO);
        Aeroport savedAeroport = aeroportRepository.save(aeroport);
        return aeroportMapper.toDto(savedAeroport);
    }

    @Override
    @Transactional
    public AeroportDTO update(Long id, AeroportDTO aeroportDTO) {
        Aeroport existingAeroport = aeroportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aéroport non trouvé avec l'id : " + id));
        
        aeroportMapper.updateAeroportFromDto(aeroportDTO, existingAeroport);
        Aeroport updatedAeroport = aeroportRepository.save(existingAeroport);
        
        return aeroportMapper.toDto(updatedAeroport);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!aeroportRepository.existsById(id)) {
            throw new ResourceNotFoundException("Aéroport non trouvé avec l'id : " + id);
        }
        aeroportRepository.deleteById(id);
    }
}
