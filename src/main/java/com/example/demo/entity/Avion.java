package com.example.demo.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "avion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Avion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "numero_avion", nullable = false, unique = true, length = 20)
    private String numeroAvion;
    
    @Column(nullable = false, length = 50)
    private String modele;
    
    @Column(nullable = false)
    private Integer capacite;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Statut statut = Statut.DISPONIBLE;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    public enum Statut {
        DISPONIBLE, EN_VOL, MAINTENANCE
    }
}

