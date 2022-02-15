package ru.home.inventoryaccounting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.home.inventoryaccounting.api.request.InventoryRequest;
import ru.home.inventoryaccounting.api.request.ParameterRequest;
import ru.home.inventoryaccounting.api.response.DtoResponse;
import ru.home.inventoryaccounting.domain.dto.InventoryDto;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.service.InventoryService;
import ru.home.inventoryaccounting.util.RequestParameterUtil;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping()
    public ResponseEntity<DtoResponse<InventoryDto>> getByAllOrFilter(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "query", defaultValue = "") String query,
            @RequestParam(name = "folderId", defaultValue = "0") long folderId,
            @RequestParam(name = "sortColumns", defaultValue = "name") String[] sortColumns,
            @RequestParam(name = "sortingDirection", defaultValue = "ASC") String sortingDirection) {

        DtoResponse<InventoryDto> response;
        ParameterRequest parameter = RequestParameterUtil.getObjectOfRequestParameters(offset, limit, query,
                folderId, sortColumns, sortingDirection);
        try {
            response = inventoryService.selectQuery(parameter);
        } catch (InvalidRequestParameteException ex) {
            response = new DtoResponse<>(false, ex.getMessage(), null, null);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoResponse<InventoryDto>> getById(@PathVariable long id) {
        DtoResponse<InventoryDto> inventoryResponse;
        try {
            inventoryResponse = new DtoResponse<>(true, "", 1L, List.of(inventoryService.findById(id)));
        } catch (NotFoundException ex) {
            inventoryResponse = new DtoResponse<>(false, ex.getMessage(), null, null);
        }
        return ResponseEntity.ok(inventoryResponse);
    }

    @PostMapping("/{id}")
    public ResponseEntity<DtoResponse<InventoryDto>> update(@PathVariable long id,
                                                                     @RequestBody InventoryRequest request) {
        DtoResponse<InventoryDto> inventoryResponse;
        try {
            inventoryResponse = new DtoResponse<>(true, "", 1L, List.of(inventoryService.update(id, request)));
        } catch (NotFoundException ex) {
            inventoryResponse = new DtoResponse<>(false, ex.getMessage(), null, null);
        }
        return ResponseEntity.ok(inventoryResponse);
    }

    @PostMapping()
    public ResponseEntity<DtoResponse<InventoryDto>> add(@RequestBody InventoryRequest request) {
        DtoResponse<InventoryDto> inventoryResponse;
        try {
            inventoryResponse = new DtoResponse<>(true, "", 1L, List.of(inventoryService.add(request)));
        } catch (NotFoundException ex) {
            inventoryResponse = new DtoResponse<>(false, ex.getMessage(), null, null);
        }
        return ResponseEntity.ok(inventoryResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DtoResponse<InventoryDto>> deleteById(@PathVariable long id) {
        DtoResponse<InventoryDto> inventoryResponse;
        try {
            inventoryService.deleteById(id);
            inventoryResponse = new DtoResponse<>(true, "Запись удалена", null, null);
        } catch (NotFoundException ex) {
            inventoryResponse = new DtoResponse<>(false, ex.getMessage(), null, null);
        }
        return ResponseEntity.ok(inventoryResponse);
    }

}
