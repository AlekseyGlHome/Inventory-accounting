package ru.home.inventoryaccounting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.home.inventoryaccounting.api.request.InventoryRequest;
import ru.home.inventoryaccounting.api.request.RequestParametersForDirectories;
import ru.home.inventoryaccounting.api.response.DtoResponse;
import ru.home.inventoryaccounting.domain.dto.InventoryDto;
import ru.home.inventoryaccounting.service.InventoryService;
import ru.home.inventoryaccounting.util.RequestParameterUtil;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/inventory")
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
        RequestParametersForDirectories parameter = RequestParameterUtil.getObjectOfRequestParameters(offset, limit, query,
                folderId, sortColumns, sortingDirection);
        return ResponseEntity.ok(inventoryService.selectQuery(parameter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoResponse<InventoryDto>> getById(@PathVariable long id) {
        return ResponseEntity.ok(new DtoResponse<>(1L, List.of(new InventoryDto(inventoryService.findById(id)))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DtoResponse<InventoryDto>> update(@PathVariable long id,
                                                            @RequestBody InventoryRequest request) {
        return ResponseEntity.ok(new DtoResponse<>(1L, List.of(inventoryService.update(id, request))));
    }

    @PostMapping()
    public ResponseEntity<DtoResponse<InventoryDto>> add(@RequestBody InventoryRequest request) {
        return ResponseEntity.ok(new DtoResponse<>(1L, List.of(inventoryService.add(request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id) {
        inventoryService.deleteById(id);
        return ResponseEntity.ok("Запись удалена");
    }

    @GetMapping("/deleting")
    public ResponseEntity<DtoResponse<InventoryDto>> getDeleted(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        RequestParametersForDirectories parameter = RequestParameterUtil.getObjectOfRequestParameters(offset, limit, "", new String[]{"name"}, "ASC");
        return ResponseEntity.ok(inventoryService.getDeleted(parameter));
    }
}
