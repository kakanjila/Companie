package com.example.demo.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RechercheVolDTO {
    private String villeDepart;
    private String villeArrivee;
    private LocalDate dateDepart;
}
