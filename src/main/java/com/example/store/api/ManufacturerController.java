package com.example.store.api;

import com.example.store.dto.request.CategoryRequest;
import com.example.store.dto.request.ManufacturerRequest;
import com.example.store.service.CategoryService;
import com.example.store.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("manufacturers")
public class ManufacturerController {
    @Autowired
    ManufacturerService manufacturerService;

    @PostMapping("add")
    public ResponseEntity<?> add(
            @ModelAttribute("name") String name) throws Exception {
                return ResponseEntity.status(HttpStatus.OK).body(
                        manufacturerService.add(ManufacturerRequest.builder()
                                        .name(name)
                                        .build())
                                        );
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(manufacturerService.getAll());
    }
}
