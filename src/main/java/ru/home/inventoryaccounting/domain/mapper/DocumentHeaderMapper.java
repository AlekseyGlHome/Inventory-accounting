package ru.home.inventoryaccounting.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.home.inventoryaccounting.domain.DTO.DocumentHeaderDTO;
import ru.home.inventoryaccounting.domain.entity.DocumentHeader;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DocumentHeaderMapper implements MaperInterface<DocumentHeader, DocumentHeaderDTO> {

    private final PartnerMapper partnerMapper;
    private final UserMapper userMapper;
    private final WarehouseMapper warehouseMapper;
    private final DocumentBodyMapper documentBodyMapper;

    /**
     * Преобразовать DocumentHeader в DocumentHeaderDTO
     *
     * @param documentHeader - элемент DocumentHeader
     * @return DocumentHeaderDTO
     */
    @Override
    public DocumentHeaderDTO convertToDTO(DocumentHeader documentHeader) {
        DocumentHeaderDTO documentHeaderDTO = DocumentHeaderDTO.builder()
                .id(documentHeader.getId())
                .amount(documentHeader.getAmount())
                .comment(documentHeader.getComment())
                .date(documentHeader.getDate())
                .deleted(documentHeader.getDeleted())
                .documentNumber(documentHeader.getDocumentNumber())
                .registered(documentHeader.getRegistered())
                .partner(partnerMapper.convertToDTO(documentHeader.getPartner()))
                .user(userMapper.convertToDTO(documentHeader.getUser()))
                .warehouse(warehouseMapper.convertToDTO(documentHeader.getWarehouse()))
                .warehouseRecipient(warehouseMapper.convertToDTO(documentHeader.getWarehouseRecipient()))
                .typeDok(documentHeader.getTypeDok())
                .build();
        documentHeaderDTO.setDocumentBody(documentBodyMapper.convertCollectionToDTO(documentHeader.getDocumentBody(), documentHeaderDTO));
        return documentHeaderDTO;
    }

    /**
     * Преобразовать коллекцию DocumentHeader в коллекцию DocumentHeaderDTO
     *
     * @param documentHeaders - элемент DocumentHeader
     * @return DocumentHeaderDTO
     */
    @Override
    public Collection<DocumentHeaderDTO> convertCollectionToDTO(Collection<DocumentHeader> documentHeaders) {
        return documentHeaders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}
