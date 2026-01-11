package com.example.demo.Controller;

import com.example.demo.dto.ReservationDTO;
import com.example.demo.service.*;
import com.example.demo.dto.PaiementDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final PaiementService paiementService;

    @GetMapping("/create")
    public String createForm(@RequestParam(name = "volId", required = false) Long volId, Model model) {
        ReservationDTO reservationDTO = new ReservationDTO();
        if (volId != null) {
            reservationDTO.setVolId(volId);
        }
        model.addAttribute("reservation", reservationDTO);
        return "reservation-create";
    }

    @PostMapping
    public String create(@ModelAttribute ReservationDTO dto, Model model) {
        try {
            ReservationDTO reservation = reservationService.create(dto);
            return "redirect:/reservations/" + reservation.getId();
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "error"; // Nom de la vue d'erreur
        }
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, Model model) {
        try {
            ReservationDTO reservation = reservationService.findById(id);
            List<PaiementDTO> paiements = paiementService.findByReservation(id);
            model.addAttribute("reservation", reservation);
            model.addAttribute("paiements", paiements);
            return "reservation-details"; // Nom de la vue de détails
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "error"; // Nom de la vue d'erreur
        }
    }
}