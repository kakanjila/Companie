package com.example.demo.repository;

import com.example.demo.entity.Passager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PassagerRepository extends JpaRepository<Passager, Long> {
    
    List<Passager> findByReservationId(Long reservationId);
    
    List<Passager> findByNomContainingIgnoreCase(String nom);
    
    List<Passager> findByPrenomContainingIgnoreCase(String prenom);
    
    List<Passager> findByNumeroPasseport(String numeroPasseport);
    
    List<Passager> findByDateNaissance(LocalDate dateNaissance);
    
    @Query("SELECT p FROM Passager p WHERE p.checkedIn = :checkedIn")
    List<Passager> findByCheckedIn(@Param("checkedIn") Boolean checkedIn);
    
    @Query("SELECT p FROM Passager p WHERE p.reservation.vol.id = :volId")
    List<Passager> findByVolId(@Param("volId") Long volId);
    
    @Query("SELECT COUNT(p) FROM Passager p WHERE p.reservation.vol.id = :volId")
    long countByVolId(@Param("volId") Long volId);
    
    boolean existsByNumeroPasseport(String numeroPasseport);
    
    @Query("SELECT p FROM Passager p WHERE p.reservation.utilisateur.id = :utilisateurId")
    List<Passager> findByUtilisateurId(@Param("utilisateurId") Long utilisateurId);
}