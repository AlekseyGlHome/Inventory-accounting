package ru.home.inventoryaccounting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.home.inventoryaccounting.api.request.ParameterRequest;
import ru.home.inventoryaccounting.api.request.WarehouseUpdateRequest;
import ru.home.inventoryaccounting.api.response.DtoResponse;
import ru.home.inventoryaccounting.domain.dto.WarehouseDto;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.service.WarehouseService;
import ru.home.inventoryaccounting.util.RequestParameterUtil;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/warehouse")
public class WarehouseController {
    private final WarehouseService warehouseService;

    @GetMapping()
    public ResponseEntity<DtoResponse<WarehouseDto>> getByAllOrFilter(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "query", defaultValue = "") String query,
            @RequestParam(name = "sortColumns", defaultValue = "name") String sortColumns,
            @RequestParam(name = "sortingDirection", defaultValue = "ASC") String sortingDirection) {

        DtoResponse<WarehouseDto> inventoryResponse;
        ParameterRequest parameter = RequestParameterUtil.getObjectOfRequestParameters(offset, limit, query, sortColumns, sortingDirection);
        try {
            inventoryResponse = warehouseService.selectQuery(parameter);
        } catch (InvalidRequestParameteException ex) {
            inventoryResponse = new DtoResponse<>(false, ex.getMessage(), null, null);
        }
        return ResponseEntity.ok(inventoryResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoResponse<WarehouseDto>> getById(@PathVariable long id) {
        DtoResponse<WarehouseDto> response;
        try {
            response = new DtoResponse<>(true, "", 1L, List.of(warehouseService.findById(id)));
        } catch (NotFoundException ex) {
            response = new DtoResponse<>(false, ex.getMessage(), null, null);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity<DtoResponse<WarehouseDto>> update(@PathVariable long id,
                                                            @RequestBody WarehouseUpdateRequest request) {
        DtoResponse<WarehouseDto> response;
        try {
            response = new DtoResponse<>(true, "", 1L, List.of(warehouseService.update(id, request)));
        } catch (NotFoundException ex) {
            response = new DtoResponse<>(false, ex.getMessage(), null, null);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<DtoResponse<WarehouseDto>> add(@RequestBody WarehouseUpdateRequest request) {
        DtoResponse<WarehouseDto> response;
        try {
            response = new DtoResponse<>(true, "", 1L, List.of(warehouseService.add(request)));
        } catch (NotFoundException ex) {
            response = new DtoResponse<>(false, ex.getMessage(), null, null);
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DtoResponse<WarehouseDto>> deleteById(@PathVariable long id) {
        DtoResponse<WarehouseDto> response;
        try {
            warehouseService.deleteById(id);
            response = new DtoResponse<>(true, "Запись удалена", null, null);
        } catch (NotFoundException ex) {
            response = new DtoResponse<>(false, ex.getMessage(), null, null);
        }
        return ResponseEntity.ok(response);
    }

}
