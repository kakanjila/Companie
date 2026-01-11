package com.example.demo.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "aeroport")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aeroport {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 3)
    private String code;
    
    @Column(nullable = false, length = 200)
    private String nom;
    
    @Column(nullable = false, length = 100)
    private String ville;
    
    @Column(nullable = false, length = 100)
    private String pays;
    
    @Column(nullable = false)
    private Boolean actif = true;
}