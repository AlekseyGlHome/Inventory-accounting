package ru.home.inventoryaccounting.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.home.inventoryaccounting.domain.DTO.DocumentBodyDTO;
import ru.home.inventoryaccounting.domain.DTO.DocumentHeaderDTO;
import ru.home.inventoryaccounting.domain.entity.DocumentBody;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DocumentBodyMapper implements MaperInterfaceForDocBody<DocumentBody, DocumentBodyDTO> {
    private final InventoryMapper inventoryMapper;

    /**
     * Преобразовать DocumentBody в DocumentBodyDTO
     *
     * @param documentBody      - элемент DocumentBody
     * @param documentHeaderDTO - ссылка на владельца
     * @return DocumentBodyDTO
     */
    @Override
    public DocumentBodyDTO convertToDTO(DocumentBody documentBody, DocumentHeaderDTO documentHeaderDTO) {
        return DocumentBodyDTO.builder()
                .id(documentBody.getId())
                .amount(documentBody.getAmount())
                .deleted(documentBody.getDeleted())
                .price(documentBody.getPrice())
                .quantity(documentBody.getQuantity())
                .inventory(inventoryMapper.convertToDTO(documentBody.getInventory()))
                .receiptDocument(documentBody.getReceiptDocument().getId())
                .serialDocumentBody(documentBody.getSerialDocumentBody().getId())
                .documentHeader(documentHeaderDTO)
                .build();
    }

    /**
     * Преобразовать коллекцию DocumentBody в коллекцию DocumentBodyDTO
     *
     * @param collection        - колекция DocumentBody
     * @param documentHeaderDTO - ссылка на владельца
     * @return Collection&lt;DocumentBodyDTO&gt;
     */
    @Override
    public Collection<DocumentBodyDTO> convertCollectionToDTO(Collection<DocumentBody> collection,
                                                              DocumentHeaderDTO documentHeaderDTO) {
        return collection.stream()
                .map((d) -> this.convertToDTO(d, documentHeaderDTO))
                .collect(Collectors.toList());
    }
}
