package ru.home.inventoryaccounting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.home.inventoryaccounting.api.request.DocumentHeaderRequest;
import ru.home.inventoryaccounting.api.request.RequestParametersForDocBody;
import ru.home.inventoryaccounting.api.response.DtoResponse;
import ru.home.inventoryaccounting.domain.dto.DocumentHeaderDto;
import ru.home.inventoryaccounting.service.DocumentsBodyService;
import ru.home.inventoryaccounting.util.RequestParameterUtil;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/document/body")
public class DocumentBodyController {
    private final DocumentsBodyService documentsBodyService;

//    @GetMapping("/{documentHeaderId}")
//    public ResponseEntity<DtoResponse<DocumentHeaderDto>> getByAllOrFilter(
//            @PathVariable() Long documentHeaderId,
//            @RequestParam(name = "sortColumns", defaultValue = "date") String[] sortColumns,
//            @RequestParam(name = "sortingDirection", defaultValue = "ASC") String sortingDirection) {
//        RequestParametersForDocBody parameter =
//                RequestParameterUtil.getObjectOfRequestParametersOfDocumentBody(documentHeaderId, sortColumns, sortingDirection);
//        return ResponseEntity.ok(documentsBodyService.selectQuery(parameter));
//    }


//    @PutMapping("/{id}")
//    public ResponseEntity<DtoResponse<DocumentHeaderDto>> update(@PathVariable long id,
//                                                                 @RequestBody DocumentHeaderRequest request) {
//        return ResponseEntity.ok(new DtoResponse<>(1L, List.of(documentsBodyService.update(id, request))));
//    }
//
//    @PostMapping()
//    public ResponseEntity<DtoResponse<DocumentHeaderDto>> add(@RequestBody DocumentHeaderRequest request) {
//        return ResponseEntity.ok(new DtoResponse<>(1L, List.of(documentsBodyService.add(request))));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteById(@PathVariable long id) {
//        documentsBodyService.deleteById(id);
//        return ResponseEntity.ok("Запись удалена");
//    }

}
