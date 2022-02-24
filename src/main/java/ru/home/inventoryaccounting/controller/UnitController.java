package ru.home.inventoryaccounting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.home.inventoryaccounting.api.request.RequestParametersForDirectories;
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
            @RequestParam(name = "sortingDirection", defaultValue = "ASC") String sortingDirection)
            throws InvalidRequestParameteException {
        RequestParametersForDirectories parameter = RequestParameterUtil.getObjectOfRequestParameters(offset, limit, query, sortColumns, sortingDirection);
        return ResponseEntity.ok(unitService.selectQuery(parameter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoResponse<UnitDto>> getById(@PathVariable long id)
            throws NotFoundException, InvalidRequestParameteException {
        return ResponseEntity.ok(new DtoResponse<>(1L, List.of(unitService.findById(id))));
    }

    @PostMapping("/{id}")
    public ResponseEntity<DtoResponse<UnitDto>> update(@PathVariable long id,
                                                       @RequestBody UnitRequest request)
            throws NotFoundException, InvalidRequestParameteException {
        return ResponseEntity.ok(new DtoResponse<>(1L, List.of(unitService.update(id, request))));
    }

    @PostMapping()
    public ResponseEntity<DtoResponse<UnitDto>> add(@RequestBody UnitRequest request) throws NotFoundException {
        return ResponseEntity.ok(new DtoResponse<>(1L, List.of(unitService.add(request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id) throws NotFoundException {
        unitService.deleteById(id);
        return ResponseEntity.ok("Запись удалена");
    }

}
