package ru.home.inventoryaccounting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.home.inventoryaccounting.api.request.PartnerRequest;
import ru.home.inventoryaccounting.api.request.RequestParametersForDirectories;
import ru.home.inventoryaccounting.api.response.DtoResponse;
import ru.home.inventoryaccounting.domain.dto.PartnerDto;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.service.PartnerService;
import ru.home.inventoryaccounting.util.RequestParameterUtil;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/header")
public class DocumentHeaderController {
    private final PartnerService partnerService;

    @GetMapping()
    public ResponseEntity<DtoResponse<PartnerDto>> getByAllOrFilter(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "query", defaultValue = "") String query,
            @RequestParam(name = "sortColumns", defaultValue = "date") String[] sortColumns,
            @RequestParam(name = "sortingDirection", defaultValue = "ASC") String sortingDirection)
            throws InvalidRequestParameteException {
        RequestParametersForDirectories parameter = RequestParameterUtil.getObjectOfRequestParameters(offset, limit, query, sortColumns, sortingDirection);
        return ResponseEntity.ok(partnerService.selectQuery(parameter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoResponse<PartnerDto>> getById(@PathVariable long id) throws NotFoundException {
        return ResponseEntity.ok(new DtoResponse<>(1L, List.of(partnerService.findById(id))));
    }

    @PostMapping("/{id}")
    public ResponseEntity<DtoResponse<PartnerDto>> update(@PathVariable long id,
                                                          @RequestBody PartnerRequest request) throws NotFoundException {
        return ResponseEntity.ok(new DtoResponse<>(1L, List.of(partnerService.update(id, request))));
    }

    @PostMapping()
    public ResponseEntity<DtoResponse<PartnerDto>> add(@RequestBody PartnerRequest request) throws NotFoundException {
        return ResponseEntity.ok(new DtoResponse<>(1L, List.of(partnerService.add(request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id) throws NotFoundException {
        partnerService.deleteById(id);
        return ResponseEntity.ok("Запись удалена");
    }

}
