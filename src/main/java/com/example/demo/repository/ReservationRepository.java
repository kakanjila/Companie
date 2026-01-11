package com.example.demo.repository;

import com.example.demo.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    
    Reservation findByReference(String reference);
    
    List<Reservation> findByUtilisateurId(Long utilisateurId);
    
    List<Reservation> findByVolId(Long volId);
    
    List<Reservation> findByStatut(Reservation.Statut statut);
    
    List<Reservation> findByDateReservationBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT r FROM Reservation r WHERE r.utilisateur.id = :userId AND r.vol.id = :volId")
    List<Reservation> findByUtilisateurAndVol(@Param("userId") Long userId, @Param("volId") Long volId);
    
    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.vol.id = :volId AND r.statut = 'CONFIRMEE'")
    long countConfirmedReservationsByVol(@Param("volId") Long volId);
    
    boolean existsByReference(String reference);
}