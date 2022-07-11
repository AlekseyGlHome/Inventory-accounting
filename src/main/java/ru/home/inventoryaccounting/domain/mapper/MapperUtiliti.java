package ru.home.inventoryaccounting.domain.mapper;


import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.home.inventoryaccounting.domain.dto.*;
import ru.home.inventoryaccounting.domain.entity.*;

import java.util.Collection;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import ru.home.inventoryaccounting.api.request.DocumentBodyRequest;
import ru.home.inventoryaccounting.service.InventoryService;

@Component
@NoArgsConstructor
public class MapperUtiliti {
    
    /**
     * Инвентарь Из Entity в Dto
     * @param inventoryEntity
     * @return 
     */
    public InventoryDto mapToInventoryDto(InventoryEntity inventoryEntity) {
        return InventoryDto.builder()
                .id(inventoryEntity.getId())
                .name(inventoryEntity.getName())
                .isDeleted(inventoryEntity.getIsDeleted())
                .folder(mapToInventoryFolderDto(inventoryEntity.getFolder()))
                .unit(mapToUnitDto(inventoryEntity.getUnit()))
                .build();
    }

    /**
     * Инвентарь Из Dto в Entity
     * @param inventoryDto
     * @return 
     */
    public InventoryEntity mapToInventoryEntity(InventoryDto inventoryDto) {
        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setId(inventoryDto.getId());
        inventoryEntity.setName(inventoryDto.getName());
        inventoryEntity.setIsDeleted(inventoryDto.isDeleted());
        inventoryEntity.setFolder(mapToInventoryFolderEntity(inventoryDto.getFolder()));
        inventoryEntity.setUnit(mapToUnitEntity(inventoryDto.getUnit()));
        return inventoryEntity;
    }

    /**
     * Папка инвентаря Из Entity в Dto
     * @param inventoryFolderEntity
     * @return 
     */
    public InventoryFolderDto mapToInventoryFolderDto(InventoryFolderEntity inventoryFolderEntity) {
        return InventoryFolderDto.builder()
                .id(inventoryFolderEntity.getId())
                .isDeleted(inventoryFolderEntity.getIsDeleted())
                .name(inventoryFolderEntity.getName())
                .build();
    }

    /**
     * Папка инвентаря Из Dto в Entity
     * @param inventoryFolderDto
     * @return 
     */
    public InventoryFolderEntity mapToInventoryFolderEntity(InventoryFolderDto inventoryFolderDto) {
        InventoryFolderEntity inventoryFolderEntity = new InventoryFolderEntity();
        inventoryFolderEntity.setId(inventoryFolderDto.getId());
        inventoryFolderEntity.setName(inventoryFolderDto.getName());
        inventoryFolderEntity.setIsDeleted(inventoryFolderDto.getIsDeleted());
        return inventoryFolderEntity;
    }

    /**
     * Единица измерения Из Entity в Dto
     * @param unitEntity
     * @return 
     */
    public UnitDto mapToUnitDto(UnitEntity unitEntity) {
        return UnitDto.builder()
                .id(unitEntity.getId())
                .isDeleted(unitEntity.getIsDeleted())
                .name(unitEntity.getName())
                .build();
    }

    /**
     * Единица измерения Из Dto в Entity
     * @param unitDto
     * @return 
     */
    public UnitEntity mapToUnitEntity(UnitDto unitDto) {
        UnitEntity unitEntity = new UnitEntity();
        unitEntity.setId(unitDto.getId());
        unitEntity.setName(unitDto.getName());
        unitEntity.setIsDeleted(unitDto.getIsDeleted());
        return unitEntity;
    }

    /**
     * Склад Из Entity в Dto
     * @param warehouseEntity
     * @return 
     */
    public WarehouseDto mapToWarehouseDto(WarehouseEntity warehouseEntity) {
        return WarehouseDto.builder()
                .id(warehouseEntity.getId())
                .name(warehouseEntity.getName())
                .isDeleted(warehouseEntity.getIsDeleted())
                .company(warehouseEntity.getCompany())
                .person(warehouseEntity.getPerson())
                .build();
    }

    /**
     * Склад Из Dto в Entity
     * @param warehouseDto
     * @return 
     */
    public WarehouseEntity mapToWarehouseEntity(WarehouseDto warehouseDto) {
        WarehouseEntity warehouseEntity = new WarehouseEntity();
        warehouseEntity.setId(warehouseDto.getId());
        warehouseEntity.setName(warehouseDto.getName());
        warehouseEntity.setIsDeleted(warehouseDto.getIsDeleted());
        warehouseEntity.setCompany(warehouseDto.getCompany());
        warehouseEntity.setPerson(warehouseDto.getPerson());
        return warehouseEntity;
    }

    /**
     * Пользователь Из Entity в Dto
     * @param userEntity
     * @return 
     */
    public UserDto mapToUserDto(UserEntity userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .isDeleted(userEntity.getIsDeleted())
                .password(userEntity.getPassword())
                .build();
    }

    /**
     * Пользователь Из Dto в Entity
     * @param userDto
     * @return 
     */
    public UserEntity mapToUserEntity(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDto.getId());
        userEntity.setName(userDto.getName());
        userEntity.setIsDeleted(userDto.getIsDeleted());
        userEntity.setPassword(userDto.getPassword());
        return userEntity;
    }

    /**
     * Партнер Из Entity в Dto
     * @param partnerEntity
     * @return 
     */
    public PartnerDto mapToPartnerDto(PartnerEntity partnerEntity) {
        return PartnerDto.builder()
                .id(partnerEntity.getId())
                .name(partnerEntity.getName())
                .isDeleted(partnerEntity.getIsDeleted())
                .build();
    }

    /**
     * Партнер Из Dto в Entity
     * @param partnerDto
     * @return 
     */
    public PartnerEntity mapToPartnerEntity(PartnerDto partnerDto) {
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setId(partnerDto.getId());
        partnerEntity.setName(partnerDto.getName());
        partnerEntity.setIsDeleted(partnerDto.getIsDeleted());
        return partnerEntity;
    }

    /**
     * Документ шапка Из Entity в Dto
     * @param documentHeaderEntity
     * @return 
     */
    public DocumentHeaderDto mapToDocumentHeaderDto(DocumentHeaderEntity documentHeaderEntity) {
        DocumentHeaderDto documentHeaderDto = DocumentHeaderDto.builder()
                .id(documentHeaderEntity.getId())
                .amount(documentHeaderEntity.getAmount())
                .comment(documentHeaderEntity.getComment())
                .date(documentHeaderEntity.getDate())
                .isDeleted(documentHeaderEntity.getIsDeleted())
                .documentNumber(documentHeaderEntity.getDocumentNumber())
                .isRegistered(documentHeaderEntity.getIsRegistered())
                .partner(mapToPartnerDto(documentHeaderEntity.getPartner()))
                .user(mapToUserDto(documentHeaderEntity.getUser()))
                .warehouse(mapToWarehouseDto(documentHeaderEntity.getWarehouse()))
//                .warehouseRecipient(mapToWarehouseDto(documentHeaderEntity.getWarehouseRecipient()))
                .typeDok(documentHeaderEntity.getTypeDok())
                .build();
        if (documentHeaderEntity.getWarehouseRecipient() != null) {
            documentHeaderDto.setWarehouseRecipient(mapToWarehouseDto(documentHeaderEntity.getWarehouseRecipient()));
        }
        //documentHeaderDto.setDocumentBody(mapToCollectionDocumentBodyDto(documentHeaderEntity.getDocumentBody(), documentHeaderDto));
        return documentHeaderDto;
    }

    /**
     * Документ шапка Из Entity в Dto
     * @param documentHeaderEntity
     * @return 
     */
    public DocumentHeaderAndBodyDto mapToDocumentHeaderAndBodyDto(DocumentHeaderEntity documentHeaderEntity) {
        DocumentHeaderAndBodyDto documentHeaderAndBodyDto = DocumentHeaderAndBodyDto.builder()
                .id(documentHeaderEntity.getId())
                .amount(documentHeaderEntity.getAmount())
                .comment(documentHeaderEntity.getComment())
                .date(documentHeaderEntity.getDate())
                .isDeleted(documentHeaderEntity.getIsDeleted())
                .documentNumber(documentHeaderEntity.getDocumentNumber())
                .isRegistered(documentHeaderEntity.getIsRegistered())
                .partner(mapToPartnerDto(documentHeaderEntity.getPartner()))
                .user(mapToUserDto(documentHeaderEntity.getUser()))
                .warehouse(mapToWarehouseDto(documentHeaderEntity.getWarehouse()))
//                .warehouseRecipient(mapToWarehouseDto(documentHeaderEntity.getWarehouseRecipient()))
                .documentBody(mapToCollectionDocumentBodyDto(documentHeaderEntity.getBodyEntitys()))
                .typeDok(documentHeaderEntity.getTypeDok())
                .build(); 
        if (documentHeaderEntity.getWarehouseRecipient() != null) {
            documentHeaderAndBodyDto.setWarehouseRecipient(mapToWarehouseDto(documentHeaderEntity.getWarehouseRecipient()));
        }
        //documentHeaderAndBodyDto.setDocumentBody(mapToCollectionDocumentBodyDto(documentHeaderEntity.getBodyEntitys()));
        return documentHeaderAndBodyDto;
    }
    
    
    /**
     * Документ шапка Из Dto в Entity
     * @param documentHeaderDto
     * @return 
     */
    public DocumentHeaderEntity mapToDocumentHeaderEntity(DocumentHeaderDto documentHeaderDto) {
        DocumentHeaderEntity documentHeaderEntity = new DocumentHeaderEntity();
        documentHeaderEntity.setId(documentHeaderDto.getId());
        documentHeaderEntity.setAmount(documentHeaderDto.getAmount());
        documentHeaderEntity.setComment(documentHeaderDto.getComment());
        documentHeaderEntity.setDate(documentHeaderDto.getDate());
        documentHeaderEntity.setIsDeleted(documentHeaderDto.getIsDeleted());
        documentHeaderEntity.setDocumentNumber(documentHeaderDto.getDocumentNumber());
        documentHeaderEntity.setIsRegistered(documentHeaderDto.getIsRegistered());
        documentHeaderEntity.setPartner(mapToPartnerEntity(documentHeaderDto.getPartner()));
        documentHeaderEntity.setUser(mapToUserEntity(documentHeaderDto.getUser()));
        documentHeaderEntity.setWarehouse(mapToWarehouseEntity(documentHeaderDto.getWarehouse()));
        //documentHeaderEntity.setWarehouseRecipient(mapToWarehouseEntity(documentHeaderDto.getWarehouseRecipient()));
        documentHeaderEntity.setTypeDok(documentHeaderDto.getTypeDok());
        //documentHeaderEntity.setDocumentBody(mapToCollectionDocumentBodyEntity(documentHeaderDto.getDocumentBody(),documentHeaderEntity));
        if (documentHeaderDto.getWarehouseRecipient() != null) {
            documentHeaderEntity.setWarehouseRecipient(mapToWarehouseEntity(documentHeaderDto.getWarehouseRecipient()));
        }
        return documentHeaderEntity;
    }


    /**
     * Документ коллекцию тело Из Entity в Dto
     * @param collection
     * @return 
     */
    public Collection<DocumentBodyDto> mapToCollectionDocumentBodyDto(Collection<DocumentBodyEntity> collection) {
        return collection.stream()
                .map((d) -> this.mapToDocumentBodyDto(d))
                .collect(Collectors.toList());
    }

    /**
     * Документ коллекцию тело Из Dto в Entity
     * @param collection
     * @param documentHeaderEntity
     * @return 
     */
    public Collection<DocumentBodyEntity> mapToCollectionDocumentBodyEntity(Collection<DocumentBodyDto> collection,
                                                                            DocumentHeaderEntity documentHeaderEntity) {
        return collection.stream()
                .map((d) -> this.mapToDocumentBodyEntity(d, documentHeaderEntity))
                .collect(Collectors.toList());
    }


    /**
     * Документ коллекцию заголовок Из Entity в Dto
     * @param collection
     * @return 
     */
    public Collection<DocumentHeaderDto> mapToCollectionDocumentHeaderDto(Collection<DocumentHeaderEntity> collection) {
        return collection.stream()
                .map(this::mapToDocumentHeaderDto)
                .collect(Collectors.toList());
    }

    /**
     * Документ коллекцию Папок Инвентаря Из Entity в Dto
     * @param collection
     * @return 
     */
    public Collection<InventoryFolderDto> mapToCollectionInventoryFolderDto(Collection<InventoryFolderEntity> collection) {
        return collection.stream()
                .map(this::mapToInventoryFolderDto)
                .collect(Collectors.toList());
    }

    /**
     * Документ коллекцию Инвентаря Из Entity в Dto
     * @param collection
     * @return 
     */
    public Collection<InventoryDto> mapToCollectionInventoryDto(Collection<InventoryEntity> collection) {
        return collection.stream()
                .map(this::mapToInventoryDto)
                .collect(Collectors.toList());
    }

    /**
     * Документ коллекцию Партнеров Из Entity в Dto
     * @param collection
     * @return 
     */
    public Collection<PartnerDto> mapToCollectionPartnerDto(Collection<PartnerEntity> collection) {
        return collection.stream()
                .map(this::mapToPartnerDto)
                .collect(Collectors.toList());
    }

    /**
     * Документ коллекцию Единиц измерения Из Entity в Dto
     * @param collection
     * @return 
     */
    public Collection<UnitDto> mapToCollectionUnitDto(Collection<UnitEntity> collection) {
        return collection.stream()
                .map(this::mapToUnitDto)
                .collect(Collectors.toList());
    }

    /**
     * Документ коллекцию Пользователей Из Entity в Dto
     * @param collection
     * @return 
     */
    public Collection<UserDto> mapToCollectionUserDto(Collection<UserEntity> collection) {
        return collection.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }


    /**
     * Документ коллекцию Складов Из Entity в Dto
     * @param collection
     * @return 
     */
    public Collection<WarehouseDto> mapToCollectionWarehouseDto(Collection<WarehouseEntity> collection) {
        return collection.stream()
                .map(this::mapToWarehouseDto)
                .collect(Collectors.toList());
    }


    /**
     * Документ тело Из Entity в Dto
     * @param documentBodyEntity
     * @return 
     */
    public DocumentBodyDto mapToDocumentBodyDto(DocumentBodyEntity documentBodyEntity) {
        return DocumentBodyDto.builder()
                .id(documentBodyEntity.getId())
                .amount(documentBodyEntity.getAmount())
                .isDeleted(documentBodyEntity.getIsDeleted())
                .price(documentBodyEntity.getPrice())
                .quantity(documentBodyEntity.getQuantity())
                .inventory(mapToInventoryDto(documentBodyEntity.getInventory()))
                //.receiptDocument(mapToDocumentHeaderDto(documentBodyEntity.getReceiptDocument()))
                //.serialDocumentBody(mapToDocumentBodyDto(documentBodyEntity.getSerialDocumentBody(),
                //        mapToDocumentHeaderDto(documentBodyEntity.getSerialDocumentBody().getDocumentHeader())))
//                .documentHeader(documentHeaderDto)
                .build();
    }


    /**
     * Документ тело Из Dto в Entity
     * @param documentBodyDto
     * @param documentHeaderEntity
     * @return 
     */
    public DocumentBodyEntity mapToDocumentBodyEntity(DocumentBodyDto documentBodyDto, DocumentHeaderEntity documentHeaderEntity) {
        DocumentBodyEntity documentBodyEntity = new DocumentBodyEntity();
        documentBodyEntity.setId(documentBodyDto.getId());
        documentBodyEntity.setAmount(documentBodyDto.getAmount());
        documentBodyEntity.setIsDeleted(documentBodyDto.getIsDeleted());
        documentBodyEntity.setPrice(documentBodyDto.getPrice());
        documentBodyEntity.setQuantity(documentBodyDto.getQuantity());
        documentBodyEntity.setInventory(mapToInventoryEntity(documentBodyDto.getInventory()));
        //documentBodyEntity.setReceiptDocument(mapToDocumentHeaderEntity(documentBodyDto.getReceiptDocument()));
        //documentBodyEntity.setSerialDocumentBody(mapToDocumentBodyEntity(documentBodyDto.getSerialDocumentBody(),
        //        mapToDocumentHeaderEntity(documentBodyDto.getSerialDocumentBody().getDocumentHeader())));
        documentBodyEntity.setDocumentHeader(documentHeaderEntity);

        return documentBodyEntity;
    }
    
    public DocumentBodyEntity mapToDocumentBodyEntity(DocumentBodyRequest documentBodyRequest, InventoryDto inventoryDto, DocumentHeaderEntity documentHeaderEntity) {
        DocumentBodyEntity documentBodyEntity = new DocumentBodyEntity();
        documentBodyEntity.setId(documentBodyRequest.getId());
        documentBodyEntity.setAmount(documentBodyRequest.getAmount());
        documentBodyEntity.setIsDeleted(documentBodyRequest.getIsDeleted());
        documentBodyEntity.setPrice(documentBodyRequest.getPrice());
        documentBodyEntity.setQuantity(documentBodyRequest.getQuantity());
        documentBodyEntity.setInventory(mapToInventoryEntity(inventoryDto));
        //documentBodyEntity.setReceiptDocument(mapToDocumentHeaderEntity(documentBodyDto.getReceiptDocument()));
        //documentBodyEntity.setSerialDocumentBody(mapToDocumentBodyEntity(documentBodyDto.getSerialDocumentBody(),
        //        mapToDocumentHeaderEntity(documentBodyDto.getSerialDocumentBody().getDocumentHeader())));
        documentBodyEntity.setDocumentHeader(documentHeaderEntity);

        return documentBodyEntity;
    }
}
