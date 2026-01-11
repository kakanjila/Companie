package com.example.demo.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "vol")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vol {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "numero_vol", nullable = false, unique = true, length = 10)
    private String numeroVol;
    
    @ManyToMany
    @JoinTable(
        name = "vol_avion",
        joinColumns = @JoinColumn(name = "vol_id"),
        inverseJoinColumns = @JoinColumn(name = "avion_id")
    )
    private Set<Avion> avions = new HashSet<>();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aeroport_depart_id", nullable = false)
    private Aeroport aeroportDepart;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aeroport_arrivee_id", nullable = false)
    private Aeroport aeroportArrivee;
    
    @Column(name = "date_depart", nullable = false)
    private LocalDateTime dateDepart;
    
    @Column(name = "date_arrivee", nullable = false)
    private LocalDateTime dateArrivee;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal prix;
    
    @Column(name = "places_disponibles", nullable = false)
    private Integer placesDisponibles;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Statut statut = Statut.PREVU;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    public enum Statut {
        PREVU, EMBARQUEMENT, EN_VOL, ATTERRI, ANNULE
    }
}
