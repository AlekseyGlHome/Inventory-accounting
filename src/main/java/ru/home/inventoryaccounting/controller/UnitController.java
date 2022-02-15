package ru.home.inventoryaccounting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.home.inventoryaccounting.api.request.ParameterRequest;
import ru.home.inventoryaccounting.api.request.UnitRequest;
import ru.home.inventoryaccounting.api.response.DtoResponse;
import ru.home.inventoryaccounting.domain.dto.UnitDto;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.service.UnitService;
import ru.home.inventoryaccounting.util.RequestParameterUtil;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/unit")
public class UnitController {
    private final UnitService unitService;

    @GetMapping()
    public ResponseEntity<DtoResponse<UnitDto>> getByAllOrFilter(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "query", defaultValue = "") String query,
            @RequestParam(name = "sortColumns", defaultValue = "name") String[] sortColumns,
            @RequestParam(name = "sortingDirection", defaultValue = "ASC") String sortingDirection) {

        DtoResponse<UnitDto> inventoryResponse;
        ParameterRequest parameter = RequestParameterUtil.getObjectOfRequestParameters(offset, limit, query, sortColumns, sortingDirection);
        try {
            inventoryResponse = unitService.selectQuery(parameter);
        } catch (InvalidRequestParameteException ex) {
            inventoryResponse = new DtoResponse<>(false, ex.getMessage(), null, null);
        }
        return ResponseEntity.ok(inventoryResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoResponse<UnitDto>> getById(@PathVariable long id) {
        DtoResponse<UnitDto> response;
        try {
            response = new DtoResponse<>(true, "", 1L, List.of(unitService.findById(id)));
        } catch (NotFoundException ex) {
            response = new DtoResponse<>(false, ex.getMessage(), null, null);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity<DtoResponse<UnitDto>> update(@PathVariable long id,
                                                            @RequestBody UnitRequest request) {
        DtoResponse<UnitDto> response;
        try {
            response = new DtoResponse<>(true, "", 1L, List.of(unitService.update(id, request)));
        } catch (NotFoundException ex) {
            response = new DtoResponse<>(false, ex.getMessage(), null, null);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<DtoResponse<UnitDto>> add(@RequestBody UnitRequest request) {
        DtoResponse<UnitDto> response;
        try {
            response = new DtoResponse<>(true, "", 1L, List.of(unitService.add(request)));
        } catch (NotFoundException ex) {
            response = new DtoResponse<>(false, ex.getMessage(), null, null);
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DtoResponse<UnitDto>> deleteById(@PathVariable long id) {
        DtoResponse<UnitDto> response;
        try {
            unitService.deleteById(id);
            response = new DtoResponse<>(true, "Запись удалена", null, null);
        } catch (NotFoundException ex) {
            response = new DtoResponse<>(false, ex.getMessage(), null, null);
        }
        return ResponseEntity.ok(response);
    }

}
