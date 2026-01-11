package com.example.demo.Controller;

import com.example.demo.dto.AeroportDTO;
import com.example.demo.service.AeroportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/aeroports")
@RequiredArgsConstructor
public class AeroportController {

    private final AeroportService aeroportService;

    @GetMapping
    public String getAll(Model model) {
        List<AeroportDTO> aeroports = aeroportService.findAll();
        model.addAttribute("aeroports", aeroports);
        model.addAttribute("pageTitle", "Liste des Aéroports");
        return "aeroports";
    }
}
