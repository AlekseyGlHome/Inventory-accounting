package ru.home.inventoryaccounting.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.home.inventoryaccounting.domain.DTO.DocumentBodyDTO;
import ru.home.inventoryaccounting.domain.entity.DocumentBody;

@Component
@RequiredArgsConstructor
public class DocumentBodyMapper {
    private final InventoryMapper inventoryMapper;

    public DocumentBodyDTO documentBodyDTO(DocumentBody documentBody) {
        return DocumentBodyDTO.builder()
                .id(documentBody.getId())
                .amount(documentBody.getAmount())
                .deleted(documentBody.getDeleted())
                .price(documentBody.getPrice())
                .quantity(documentBody.getQuantity())
                .inventory(inventoryMapper.inventoryToDTO(documentBody.getInventory()))
                .receiptDocument(documentBody.getReceiptDocument().getId())
                .serialDocumentBody(documentBody.getSerialDocumentBody().getId())
                .build();
    }
}
