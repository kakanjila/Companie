package com.example.demo.Controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.PassagerDTO;
import com.example.demo.service.PassagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passagers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PassagerController {

    private final PassagerService passagerService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PassagerDTO>>> getAll() {
        List<PassagerDTO> passagers = passagerService.findAll();
        return ResponseEntity.ok(ApiResponse.success(passagers));
    }

    // ... autres méthodes CRUD ...
}
