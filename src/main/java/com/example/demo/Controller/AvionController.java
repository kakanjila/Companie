package com.example.demo.Controller;

import com.example.demo.dto.AvionDTO;
import com.example.demo.service.AvionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/avions")
@RequiredArgsConstructor
public class AvionController {

    private final AvionService avionService;

    @GetMapping
    public String getAll(Model model) {
        List<AvionDTO> avions = avionService.findAll();
        model.addAttribute("avions", avions);
        model.addAttribute("pageTitle", "Liste des Avions");
        return "avions";
    }
}
