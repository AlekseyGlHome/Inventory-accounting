package ru.home.inventoryaccounting.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.home.inventoryaccounting.domain.DTO.DocumentBodyDTO;
import ru.home.inventoryaccounting.domain.DTO.DocumentHeaderDTO;
import ru.home.inventoryaccounting.domain.DTO.InventoryFolderDTO;
import ru.home.inventoryaccounting.domain.entity.DocumentBody;
import ru.home.inventoryaccounting.domain.entity.DocumentHeader;
import ru.home.inventoryaccounting.domain.entity.InventoryFolder;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DocumentHeaderMapper {
    private final PartnerMapper partnerMapper;
    private final UserMapper userMapper;
    private final WarehouseMapper warehouseMapper;
    private final DocumentBodyMapper documentBodyMapper;

    public DocumentHeaderDTO documentHeaderToDTO(DocumentHeader documentHeader) {
        return DocumentHeaderDTO.builder()
                .id(documentHeader.getId())
                .amount(documentHeader.getAmount())
                .comment(documentHeader.getComment())
                .date(documentHeader.getDate())
                .deleted(documentHeader.getDeleted())
                .documentNumber(documentHeader.getDocumentNumber())
                .registered(documentHeader.getRegistered())
                .partner(partnerMapper.partnerToDTO(documentHeader.getPartner()))
                .user(userMapper.userToDTO(documentHeader.getUser()))
                .warehouse(warehouseMapper.warehouseToDTO(documentHeader.getWarehouse()))
                .warehouseRecipient(warehouseMapper.warehouseToDTO(documentHeader.getWarehouseRecipient()))
                .typeDok(documentHeader.getTypeDok())
                .documentBody(getCollectionBodyDto(documentHeader.getDocumentBody()))
                .build();
    }

    private Collection<DocumentBodyDTO> getCollectionBodyDto(Collection<DocumentBody> collection){

        return collection.stream().map(documentBodyMapper::documentBodyDTO).collect(Collectors.toList());
    }

}
