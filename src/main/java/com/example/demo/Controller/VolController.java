package com.example.demo.Controller;

import com.example.demo.dto.RechercheVolDTO;
import com.example.demo.dto.VolDTO;
import com.example.demo.service.VolService;
import com.example.demo.service.AeroportService;
import com.example.demo.dto.AeroportDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
@RequestMapping("/vols")
@RequiredArgsConstructor
public class VolController {

    private final VolService volService;
    private final AeroportService aeroportService;

    @GetMapping
    public String getAll(Model model) {
        List<VolDTO> vols = volService.findAll();
        model.addAttribute("vols", vols);
        return "vols-list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("vol", new VolDTO());
        List<AeroportDTO> aeroports = aeroportService.findAll();
        model.addAttribute("aeroports", aeroports);
        return "vol-create";
    }

    @PostMapping
    public String create(@ModelAttribute VolDTO volDTO, Model model) {
        try {
            volService.create(volDTO);
            return "redirect:/vols";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "vol-create";
        }
    }

    @GetMapping("/rechercher")
    public String rechercherForm(Model model) {
        model.addAttribute("rechercheVolDTO", new RechercheVolDTO());
        List<AeroportDTO> aeroports = aeroportService.findAll();
        model.addAttribute("aeroports", aeroports);
        return "vol-recherche";
    }

    @PostMapping("/rechercher")
    public String rechercher(@ModelAttribute RechercheVolDTO dto, Model model) {
        List<VolDTO> vols = volService.rechercherVols(dto);
        model.addAttribute("vols", vols);
        return "vol-recherche-resultats";
    }
}
