package com.example.demo.service.impl;

import com.example.demo.dto.UtilisateurDTO;
import com.example.demo.entity.Utilisateur;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.UtilisateurMapper;
import com.example.demo.repository.UtilisateurRepository;
import com.example.demo.service.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UtilisateurServiceImpl implements UtilisateurService {
    
    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurMapper utilisateurMapper;
    
    @Override
    @Transactional(readOnly = true)
    public List<UtilisateurDTO> findAll() {
        return utilisateurRepository.findAll()
                .stream()
                .map(utilisateurMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public UtilisateurDTO findById(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id));
        return utilisateurMapper.toDto(utilisateur);
    }
    
    @Override
    @Transactional(readOnly = true)
    public UtilisateurDTO findByEmail(String email) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'email : " + email));
        return utilisateurMapper.toDto(utilisateur);
    }
    
    @Override
    public UtilisateurDTO create(UtilisateurDTO utilisateurDTO) {
        // Vérifier l'unicité de l'email
        if (utilisateurRepository.existsByEmail(utilisateurDTO.getEmail())) {
            throw new RuntimeException("Un utilisateur avec l'email " + utilisateurDTO.getEmail() + " existe déjà");
        }
        
        Utilisateur utilisateur = utilisateurMapper.toEntity(utilisateurDTO);
        
        // Stocker le mot de passe en clair (ou utiliser un autre mécanisme de hachage si nécessaire)
        // utilisateur.setMotDePasse(utilisateurDTO.getMotDePasse());
        
        // S'assurer que le statut actif est défini
        if (utilisateur.getActif() == null) {
            utilisateur.setActif(true);
        }
        
        Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);
        return utilisateurMapper.toDto(savedUtilisateur);
    }
    
    @Override
    public UtilisateurDTO update(Long id, UtilisateurDTO utilisateurDTO) {
        Utilisateur existingUtilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id));
        
        // Vérifier l'unicité de l'email
        if (!existingUtilisateur.getEmail().equals(utilisateurDTO.getEmail()) 
                && utilisateurRepository.existsByEmail(utilisateurDTO.getEmail())) {
            throw new RuntimeException("Un autre utilisateur avec l'email " + utilisateurDTO.getEmail() + " existe déjà");
        }
        
        // Mettre à jour les champs
        existingUtilisateur.setNom(utilisateurDTO.getNom());
        existingUtilisateur.setPrenom(utilisateurDTO.getPrenom());
        existingUtilisateur.setEmail(utilisateurDTO.getEmail());
        existingUtilisateur.setTelephone(utilisateurDTO.getTelephone());
        existingUtilisateur.setRole(utilisateurDTO.getRole());
        existingUtilisateur.setActif(utilisateurDTO.getActif());
        
        // Mettre à jour le mot de passe si fourni
        if (utilisateurDTO.getMotDePasse() != null && !utilisateurDTO.getMotDePasse().isEmpty()) {
            existingUtilisateur.setMotDePasse(utilisateurDTO.getMotDePasse());
        }
        
        Utilisateur updatedUtilisateur = utilisateurRepository.save(existingUtilisateur);
        return utilisateurMapper.toDto(updatedUtilisateur);
    }
    
    @Override
    public UtilisateurDTO updatePartial(Long id, UtilisateurDTO.UpdateDTO updateDTO) {
        Utilisateur existingUtilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id));
        
        // Mettre à jour uniquement les champs non nuls
        if (updateDTO.getNom() != null) {
            existingUtilisateur.setNom(updateDTO.getNom());
        }
        if (updateDTO.getPrenom() != null) {
            existingUtilisateur.setPrenom(updateDTO.getPrenom());
        }
        if (updateDTO.getTelephone() != null) {
            existingUtilisateur.setTelephone(updateDTO.getTelephone());
        }
        if (updateDTO.getActif() != null) {
            existingUtilisateur.setActif(updateDTO.getActif());
        }
        
        Utilisateur updatedUtilisateur = utilisateurRepository.save(existingUtilisateur);
        return utilisateurMapper.toDto(updatedUtilisateur);
    }
    
    @Override
    public void delete(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id));
        
        // Vérifier si l'utilisateur a des réservations
        // (à implémenter si nécessaire)
        
        utilisateurRepository.delete(utilisateur);
    }
    
    @Override
    public void deactivate(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id));
        
        utilisateur.setActif(false);
        utilisateurRepository.save(utilisateur);
    }
    
    @Override
    public void activate(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id));
        
        utilisateur.setActif(true);
        utilisateurRepository.save(utilisateur);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<UtilisateurDTO> findByRole(Utilisateur.Role role) {
        return utilisateurRepository.findByRole(role)
                .stream()
                .map(utilisateurMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<UtilisateurDTO> findByActif(Boolean actif) {
        return utilisateurRepository.findByActif(actif)
                .stream()
                .map(utilisateurMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<UtilisateurDTO> searchByNom(String nom) {
        return utilisateurRepository.findByNomContainingIgnoreCase(nom)
                .stream()
                .map(utilisateurMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<UtilisateurDTO> searchByPrenom(String prenom) {
        return utilisateurRepository.findByPrenomContainingIgnoreCase(prenom)
                .stream()
                .map(utilisateurMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return utilisateurRepository.existsByEmail(email);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean isEmailUnique(Long id, String email) {
        return utilisateurRepository.findByEmail(email)
                .map(u -> u.getId().equals(id))
                .orElse(true);
    }
    
    @Override
    @Transactional(readOnly = true)
    public long countByRole(Utilisateur.Role role) {
        return utilisateurRepository.findByRole(role).size();
    }
    
    @Override
    @Transactional(readOnly = true)
    public long countActifs() {
        return utilisateurRepository.findByActif(true).size();
    }
    
    @Override
    @Transactional(readOnly = true)
    public UtilisateurDTO authenticate(String email, String password) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'email : " + email));
        
        // Vérifier le mot de passe (comparaison directe sans encodage)
        if (!password.equals(utilisateur.getMotDePasse())) {
            throw new RuntimeException("Mot de passe incorrect");
        }
        
        // Vérifier si le compte est actif
        if (!utilisateur.getActif()) {
            throw new RuntimeException("Le compte est désactivé");
        }
        
        return utilisateurMapper.toDto(utilisateur);
    }
    
    @Override
    public UtilisateurDTO changeRole(Long id, Utilisateur.Role newRole) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id));
        
        utilisateur.setRole(newRole);
        Utilisateur updatedUtilisateur = utilisateurRepository.save(utilisateur);
        return utilisateurMapper.toDto(updatedUtilisateur);
    }
}