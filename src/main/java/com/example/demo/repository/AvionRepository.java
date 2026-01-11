package com.example.demo.repository;

import com.example.demo.entity.Avion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvionRepository extends JpaRepository<Avion, Long> {
    
    List<Avion> findByStatut(Avion.Statut statut);
    
    Avion findByNumeroAvion(String numeroAvion);
    
    boolean existsByNumeroAvion(String numeroAvion);
    
    List<Avion> findByModele(String modele);
    
    List<Avion> findByCapaciteGreaterThanEqual(Integer capacite);
} 