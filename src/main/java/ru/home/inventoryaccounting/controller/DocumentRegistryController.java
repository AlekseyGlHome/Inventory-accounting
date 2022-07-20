package ru.home.inventoryaccounting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.home.inventoryaccounting.api.request.RequestParametersForDocHeader;
import ru.home.inventoryaccounting.api.response.DtoResponse;
import ru.home.inventoryaccounting.domain.dto.DocumentHeaderDto;
import ru.home.inventoryaccounting.domain.enums.SortingDirection;
import ru.home.inventoryaccounting.service.DocumentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/registration")
public class DocumentRegistryController {
    private final DocumentService documentService;
   // private final

    @PostMapping("/{id}")
    public ResponseEntity<Boolean> addToRegistration(@PathVariable Long id) {
        documentService.addToRegistration(id);
        return ResponseEntity.ok(true);
    }

    @GetMapping()
    public ResponseEntity<DtoResponse<DocumentHeaderDto>> getAllRegistration() {
        DtoResponse<DocumentHeaderDto> listDocReg = documentService.getDocumentRegistration(new RequestParametersForDocHeader(0,50,"",null,null,0L,0L,0,new String[]{"id"}, SortingDirection.ASC));
        return ResponseEntity.ok(listDocReg);
    }



}
