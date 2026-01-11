package com.example.demo.service;

import com.example.demo.dto.AeroportDTO;
import java.util.List;

public interface AeroportService {
    List<AeroportDTO> findAll();
    AeroportDTO findById(Long id);
    AeroportDTO findByCode(String code);
    List<AeroportDTO> findActifs();
    AeroportDTO save(AeroportDTO aeroportDTO);
    AeroportDTO update(Long id, AeroportDTO aeroportDTO);
    void delete(Long id);
}
