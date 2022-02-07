package ru.home.inventoryaccounting.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.home.inventoryaccounting.domain.DTO.DocumentHeaderDTO;
import ru.home.inventoryaccounting.domain.entity.DocumentHeaderEntity;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DocumentHeaderMapper implements MaperInterface<DocumentHeaderEntity, DocumentHeaderDTO> {

    private final PartnerMapper partnerMapper;
    private final UserMapper userMapper;
    private final WarehouseMapper warehouseMapper;
    private final DocumentBodyMapper documentBodyMapper;

    /**
     * Преобразовать DocumentHeader в DocumentHeaderDTO
     *
     * @param documentHeaderEntity - элемент DocumentHeader
     * @return DocumentHeaderDTO
     */
    @Override
    public DocumentHeaderDTO convertToDTO(DocumentHeaderEntity documentHeaderEntity) {
        DocumentHeaderDTO documentHeaderDTO = DocumentHeaderDTO.builder()
                .id(documentHeaderEntity.getId())
                .amount(documentHeaderEntity.getAmount())
                .comment(documentHeaderEntity.getComment())
                .date(documentHeaderEntity.getDate())
                .deleted(documentHeaderEntity.getDeleted())
                .documentNumber(documentHeaderEntity.getDocumentNumber())
                .registered(documentHeaderEntity.getRegistered())
                .partner(partnerMapper.convertToDTO(documentHeaderEntity.getPartner()))
                .user(userMapper.convertToDTO(documentHeaderEntity.getUser()))
                .warehouse(warehouseMapper.convertToDTO(documentHeaderEntity.getWarehouse()))
                .warehouseRecipient(warehouseMapper.convertToDTO(documentHeaderEntity.getWarehouseRecipient()))
                .typeDok(documentHeaderEntity.getTypeDok())
                .build();
        documentHeaderDTO.setDocumentBody(documentBodyMapper.convertCollectionToDTO(documentHeaderEntity.getDocumentBody(), documentHeaderDTO));
        return documentHeaderDTO;
    }

    /**
     * Преобразовать коллекцию DocumentHeader в коллекцию DocumentHeaderDTO
     *
     * @param documentHeaderEntities - элемент DocumentHeader
     * @return DocumentHeaderDTO
     */
    @Override
    public Collection<DocumentHeaderDTO> convertCollectionToDTO(Collection<DocumentHeaderEntity> documentHeaderEntities) {
        return documentHeaderEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}
