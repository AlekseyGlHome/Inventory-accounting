package ru.home.inventoryaccounting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.home.inventoryaccounting.api.request.InventoryFoldeRequest;
import ru.home.inventoryaccounting.api.request.RequestParametersForDirectories;
import ru.home.inventoryaccounting.api.response.DtoResponse;
import ru.home.inventoryaccounting.domain.dto.InventoryFolderDto;
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
        RequestParametersForDirectories parameter = RequestParameterUtil.getObjectOfRequestParameters(offset, limit, query, sortColumns, sortingDirection);
        return ResponseEntity.ok(inventoryFolderService.selectQuery(parameter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoResponse<InventoryFolderDto>> getById(@PathVariable long id) {

        return ResponseEntity.ok(new DtoResponse<>(1L, List.of(inventoryFolderService.findById(id))));
    }

    @PostMapping("/{id}")
    public ResponseEntity<DtoResponse<InventoryFolderDto>> update(@PathVariable long id,
                                                                  @RequestBody InventoryFoldeRequest request) {
        return ResponseEntity.ok(new DtoResponse<>(1L, List.of(inventoryFolderService.update(id, request))));
    }

    @PostMapping()
    public ResponseEntity<DtoResponse<InventoryFolderDto>> add(@RequestBody InventoryFoldeRequest request) {

        return ResponseEntity.ok(new DtoResponse<>(1L, List.of(inventoryFolderService.add(request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id) {
        inventoryFolderService.deleteById(id);
        return ResponseEntity.ok("Запись удалена");
    }

}
