package ru.home.inventoryaccounting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.home.inventoryaccounting.api.request.RequestParametersForDirectories;
import ru.home.inventoryaccounting.api.request.WarehouseRequest;
import ru.home.inventoryaccounting.api.response.DtoResponse;
import ru.home.inventoryaccounting.domain.dto.WarehouseDto;
import ru.home.inventoryaccounting.service.WarehouseService;
import ru.home.inventoryaccounting.util.RequestParameterUtil;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/warehouse")
public class WarehouseController {
    private final WarehouseService warehouseService;

    @GetMapping()
    public ResponseEntity<DtoResponse<WarehouseDto>> getByAllOrFilter(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "query", defaultValue = "") String query,
            @RequestParam(name = "sortColumns", defaultValue = "name") String[] sortColumns,
            @RequestParam(name = "sortingDirection", defaultValue = "ASC") String sortingDirection) {
        RequestParametersForDirectories parameter = RequestParameterUtil.getObjectOfRequestParameters(offset, limit, query, sortColumns, sortingDirection);
        return ResponseEntity.ok(warehouseService.selectQuery(parameter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoResponse<WarehouseDto>> getById(@PathVariable long id) {
        return ResponseEntity.ok(new DtoResponse<>(1L, List.of(warehouseService.findById(id))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DtoResponse<WarehouseDto>> update(@PathVariable long id,
                                                            @RequestBody WarehouseRequest request) {
        return ResponseEntity.ok(new DtoResponse<>(1L, List.of(warehouseService.update(id, request))));
    }

    @PostMapping()
    public ResponseEntity<DtoResponse<WarehouseDto>> add(@RequestBody WarehouseRequest request) {
        return ResponseEntity.ok(new DtoResponse<>(1L, List.of(warehouseService.add(request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id) {
        warehouseService.deleteById(id);
        return ResponseEntity.ok("Запись удалена");
    }

}
