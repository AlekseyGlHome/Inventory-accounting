package ru.home.inventoryaccounting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.home.inventoryaccounting.api.request.InventoryUpdateRequest;
import ru.home.inventoryaccounting.api.request.ParameterRequest;
import ru.home.inventoryaccounting.api.response.DTOResponse;
import ru.home.inventoryaccounting.domain.dto.InventoryDto;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.service.InventoryService;

@RestController
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/inventory")
    public ResponseEntity<Object> getByAllOrFilter(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "query", defaultValue = "") String query,
            @RequestParam(name = "folderId", defaultValue = "0") long folderId,
            @RequestParam(name = "sortColumns", defaultValue = "name") String sortColumns,
            @RequestParam(name = "sortingDirection", defaultValue = "ASC") String sortingDirection) {


        DTOResponse<InventoryDto> inventoryResponse;
        ParameterRequest parameter = inventoryService.getRequestParameters(offset, limit, query,
                folderId, sortColumns, sortingDirection);
        try {
            inventoryResponse = inventoryService.selectQuery(parameter);
        } catch (InvalidRequestParameteException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok(inventoryResponse);
    }

    @GetMapping("/inventory/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        InventoryDto inventoryDTO;
        try {
            inventoryDTO = inventoryService.findById(id);
            return ResponseEntity.ok(inventoryDTO);
        } catch (NotFoundException ex) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("/inventory/{id}")
    public ResponseEntity<?> update(@PathVariable long id,
                                    @RequestBody InventoryUpdateRequest request) {
        InventoryDto inventoryDTO;
        try {
            inventoryDTO = inventoryService.update(id, request);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok(inventoryDTO);
    }


    @PostMapping("/inventory")
    public ResponseEntity<?> add(@RequestBody InventoryUpdateRequest request) {
        InventoryDto inventoryDTO;
        try {
            inventoryDTO = inventoryService.add(request);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok(inventoryDTO);
    }

}
