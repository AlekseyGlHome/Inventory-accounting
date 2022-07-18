package ru.home.inventoryaccounting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.home.inventoryaccounting.service.DocumentRegistryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/registration")
public class DocumentRegistryController {
    private final DocumentRegistryService documentRegistryService;

    @PostMapping("/{id}")
    public ResponseEntity<Boolean> addToRegistration(@PathVariable Long id) {

        documentRegistryService.addToRegistration(id);

        return ResponseEntity.ok(true);
    }

}
