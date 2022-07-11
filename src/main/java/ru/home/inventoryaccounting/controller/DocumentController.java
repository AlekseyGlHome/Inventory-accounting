package ru.home.inventoryaccounting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.home.inventoryaccounting.api.request.DocumentHeaderRequest;
import ru.home.inventoryaccounting.api.request.RequestParametersForDocHeader;
import ru.home.inventoryaccounting.api.response.DtoResponse;
import ru.home.inventoryaccounting.domain.dto.DocumentHeaderDto;
import ru.home.inventoryaccounting.service.DocumentService;
import ru.home.inventoryaccounting.util.RequestParameterUtil;

import java.time.LocalDate;
import java.util.List;
import ru.home.inventoryaccounting.domain.dto.DocumentHeaderAndBodyDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/documents")
public class DocumentController {
    private final DocumentService documentsHeaderService;

    @GetMapping()
    public ResponseEntity<DtoResponse<DocumentHeaderDto>> getByAllOrFilter(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "query", defaultValue = "") String query,
            @RequestParam(name = "intervalStart", defaultValue = "") LocalDate intervalStart,
            @RequestParam(name = "intervalEnd", defaultValue = "") LocalDate intervalEnd,
            @RequestParam(name = "partnerId", defaultValue = "0") Long partnerId,
            @RequestParam(name = "warehouseId", defaultValue = "0") Long warehouseId,
            @RequestParam(name = "typeDok", defaultValue = "0") Integer typeDok,
            @RequestParam(name = "sortColumns", defaultValue = "date") String[] sortColumns,
            @RequestParam(name = "sortingDirection", defaultValue = "ASC") String sortingDirection) {
        RequestParametersForDocHeader parameter =
                RequestParameterUtil.getObjectOfRequestParametersOfDocumentHeader(offset, limit, query, intervalStart,
                        intervalEnd, partnerId, warehouseId, typeDok, sortColumns, sortingDirection);
        return ResponseEntity.ok(documentsHeaderService.selectQuery(parameter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoResponse<DocumentHeaderAndBodyDto>> getById(@PathVariable long id) {
        return ResponseEntity.ok(new DtoResponse<>(1L, List.of(documentsHeaderService.getFullById(id))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DtoResponse<DocumentHeaderAndBodyDto>> update(@PathVariable long id,
                                                          @RequestBody DocumentHeaderRequest request) {
        return ResponseEntity.ok(new DtoResponse<>(1L, List.of(documentsHeaderService.update(id, request))));
    }

    @PostMapping()
    public ResponseEntity<DtoResponse<DocumentHeaderAndBodyDto>> add(@RequestBody DocumentHeaderRequest request) {
        return ResponseEntity.ok(new DtoResponse<>(1L, List.of(documentsHeaderService.add(request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id) {
        documentsHeaderService.deleteById(id);
        return ResponseEntity.ok("Запись удалена");
    }

}
