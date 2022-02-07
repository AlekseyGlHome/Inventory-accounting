package ru.home.inventoryaccounting.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.home.inventoryaccounting.domain.DTO.DocumentBodyDTO;
import ru.home.inventoryaccounting.domain.DTO.DocumentHeaderDTO;
import ru.home.inventoryaccounting.domain.entity.DocumentBodyEntity;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DocumentBodyMapper implements MaperInterfaceForDocBody<DocumentBodyEntity, DocumentBodyDTO> {
    private final InventoryMapper inventoryMapper;

    /**
     * Преобразовать DocumentBody в DocumentBodyDTO
     *
     * @param documentBodyEntity      - элемент DocumentBody
     * @param documentHeaderDTO - ссылка на владельца
     * @return DocumentBodyDTO
     */
    @Override
    public DocumentBodyDTO convertToDTO(DocumentBodyEntity documentBodyEntity, DocumentHeaderDTO documentHeaderDTO) {
        return DocumentBodyDTO.builder()
                .id(documentBodyEntity.getId())
                .amount(documentBodyEntity.getAmount())
                .deleted(documentBodyEntity.getDeleted())
                .price(documentBodyEntity.getPrice())
                .quantity(documentBodyEntity.getQuantity())
                .inventory(inventoryMapper.mapToInventoryDto(documentBodyEntity.getInventory()))
                .receiptDocument(documentBodyEntity.getReceiptDocument().getId())
                .serialDocumentBody(documentBodyEntity.getSerialDocumentBody().getId())
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
    public Collection<DocumentBodyDTO> convertCollectionToDTO(Collection<DocumentBodyEntity> collection,
                                                              DocumentHeaderDTO documentHeaderDTO) {
        return collection.stream()
                .map((d) -> this.convertToDTO(d, documentHeaderDTO))
                .collect(Collectors.toList());
    }
}
