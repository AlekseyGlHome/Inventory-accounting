package ru.home.inventoryaccounting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.home.inventoryaccounting.api.request.InventoryFoldeRequest;
import ru.home.inventoryaccounting.api.request.RequestParametersForDirectories;
import ru.home.inventoryaccounting.api.response.DtoResponse;
import ru.home.inventoryaccounting.domain.dto.InventoryFolderDto;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.service.InventoryFolderService;
import ru.home.inventoryaccounting.util.RequestParameterUtil;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/folder")
public class InventoryFolderController {
    private final InventoryFolderService inventoryFolderService;

    @GetMapping()
    public ResponseEntity<DtoResponse<InventoryFolderDto>> getByAllOrFilter(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "query", defaultValue = "") String query,
            @RequestParam(name = "sortColumns", defaultValue = "name") String[] sortColumns,
            @RequestParam(name = "sortingDirection", defaultValue = "ASC") String sortingDirection) {

        DtoResponse<InventoryFolderDto> response;
        RequestParametersForDirectories parameter = RequestParameterUtil.getObjectOfRequestParameters(offset, limit, query, sortColumns, sortingDirection);
        try {
            response = inventoryFolderService.selectQuery(parameter);
        } catch (InvalidRequestParameteException ex) {
            response = new DtoResponse<>(false, ex.getMessage(), null, null);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoResponse<InventoryFolderDto>> getById(@PathVariable long id) {
        DtoResponse<InventoryFolderDto> response;
        try {
            response = new DtoResponse<>(true, "", 1L, List.of(inventoryFolderService.findById(id)));
        } catch (NotFoundException ex) {
            response = new DtoResponse<>(false, ex.getMessage(), null, null);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity<DtoResponse<InventoryFolderDto>> update(@PathVariable long id,
                                                            @RequestBody InventoryFoldeRequest request) {
        DtoResponse<InventoryFolderDto> response;
        try {
            response = new DtoResponse<>(true, "", 1L, List.of(inventoryFolderService.update(id, request)));
        } catch (NotFoundException ex) {
            response = new DtoResponse<>(false, ex.getMessage(), null, null);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<DtoResponse<InventoryFolderDto>> add(@RequestBody InventoryFoldeRequest request) {
        DtoResponse<InventoryFolderDto> response;
        try {
            response = new DtoResponse<>(true, "", 1L, List.of(inventoryFolderService.add(request)));
        } catch (NotFoundException ex) {
            response = new DtoResponse<>(false, ex.getMessage(), null, null);
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DtoResponse<InventoryFolderDto>> deleteById(@PathVariable long id) {
        DtoResponse<InventoryFolderDto> response;
        try {
            inventoryFolderService.deleteById(id);
            response = new DtoResponse<>(true, "Запись удалена", null, null);
        } catch (NotFoundException ex) {
            response = new DtoResponse<>(false, ex.getMessage(), null, null);
        }
        return ResponseEntity.ok(response);
    }

}
