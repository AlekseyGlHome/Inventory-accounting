package ru.home.inventoryaccounting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.home.inventoryaccounting.api.response.DTOResponse;
import ru.home.inventoryaccounting.domain.DTO.InventoryDTO;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.service.InventoryService;

@RestController
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/inventory")
    public ResponseEntity getInventoryByFilter(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "query", defaultValue = "") String query,
            @RequestParam(name = "folderId", defaultValue = "0") long folderId) {

        DTOResponse<InventoryDTO> inventoryResponse;
        try {
            inventoryResponse = inventoryService.findByNameLikeAndFolderId(offset, limit, query, folderId);
        } catch (InvalidRequestParameteException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok(inventoryResponse);
    }

    @GetMapping("/inventory/{id}")
    public ResponseEntity<Object> getInventoryById(@PathVariable long id) {
        InventoryDTO inventoryDTO;
        try {
            inventoryDTO = inventoryService.findById(id);
            return ResponseEntity.ok(inventoryDTO);
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
