package ru.home.inventoryaccounting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/registration")
public class DocumentRegistryController {

    @PostMapping("/{id}")
    public ResponseEntity<Boolean> registry(@PathVariable Long id) {


        return ResponseEntity.ok(true);
    }

}
