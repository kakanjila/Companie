package com.example.demo.Controller;

import com.example.demo.dto.UtilisateurDTO;
import com.example.demo.service.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/utilisateurs")
@RequiredArgsConstructor
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    @GetMapping
    public String getAll(Model model) {
        List<UtilisateurDTO> utilisateurs = utilisateurService.findAll();
        model.addAttribute("utilisateurs", utilisateurs);
        model.addAttribute("pageTitle", "Liste des Utilisateurs");
        return "utilisateurs";
    }
}