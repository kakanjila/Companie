package com.example.demo.Controller;
import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.PaiementDTO;
import com.example.demo.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/paiements")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PaiementController {
    
    private final PaiementService paiementService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<PaiementDTO>>> getAll() {
        List<PaiementDTO> paiements = paiementService.findAll();
        return ResponseEntity.ok(ApiResponse.success(paiements));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PaiementDTO>> getById(@PathVariable Long id) {
        try {
            PaiementDTO paiement = paiementService.findById(id);
            return ResponseEntity.ok(ApiResponse.success(paiement));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<PaiementDTO>> create(@Valid @RequestBody PaiementDTO.CreateDTO dto) {
        try {
            PaiementDTO paiement = paiementService.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(paiement));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/{id}/confirmer")
    public String confirmerPaiement(@PathVariable Long id, Model model) {
        try {
            PaiementDTO paiement = paiementService.confirmerPaiement(id);
            return "redirect:/reservations/" + paiement.getReservationId();
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "error"; // Nom de la vue d'erreur
        }
    }
}
