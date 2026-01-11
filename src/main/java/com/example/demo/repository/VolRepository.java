package com.example.demo.repository;

import com.example.demo.entity.Vol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VolRepository extends JpaRepository<Vol, Long> {
    
    Vol findByNumeroVol(String numeroVol);
    
    List<Vol> findByStatut(Vol.Statut statut);
    
    @Query("SELECT v FROM Vol v JOIN v.avions a WHERE a.id = :avionId")
    List<Vol> findByAvionId(@Param("avionId") Long avionId);
    
    List<Vol> findByAeroportDepartId(Long aeroportId);
    
    List<Vol> findByAeroportArriveeId(Long aeroportId);
    
    List<Vol> findByDateDepartBetween(LocalDateTime start, LocalDateTime end);
    
    List<Vol> findByDateArriveeBetween(LocalDateTime start, LocalDateTime end);
    
    List<Vol> findByPlacesDisponiblesGreaterThan(Integer places);
    
    List<Vol> findByPrixLessThanEqual(BigDecimal maxPrix);
    
    @Query("SELECT v FROM Vol v WHERE v.aeroportDepart.id = :departId AND v.aeroportArrivee.id = :arriveeId")
    List<Vol> findByAeroportDepartAndArrivee(@Param("departId") Long departId, @Param("arriveeId") Long arriveeId);
    
    @Query("SELECT v FROM Vol v WHERE v.aeroportDepart.ville = :villeDepart AND v.aeroportArrivee.ville = :villeArrivee AND v.dateDepart >= :dateDepart")
    List<Vol> findByVilleDepartAndVilleArriveeAndDateDepart(@Param("villeDepart") String villeDepart, @Param("villeArrivee") String villeArrivee, @Param("dateDepart") LocalDateTime dateDepart);
    
    @Query("SELECT v FROM Vol v WHERE v.dateDepart >= :dateDebut AND v.dateDepart <= :dateFin")
    List<Vol> findVolsBetweenDates(@Param("dateDebut") LocalDateTime dateDebut, @Param("dateFin") LocalDateTime dateFin);
    
    boolean existsByNumeroVol(String numeroVol);

    List<Vol> findByStatutAndPlacesDisponiblesGreaterThan(Vol.Statut statut, Integer places);
}