package ru.home.inventoryaccounting.domain.mapper;


import ru.home.inventoryaccounting.domain.DTO.*;
import ru.home.inventoryaccounting.domain.entity.*;

public class MapperUtiliti {

    /**
     * Инвентарь Из Entity в DTO
     */
    public InventoryDTO mapToInventoryDto(InventoryEntity inventoryEntity) {
        return InventoryDTO.builder()
                .id(inventoryEntity.getId())
                .name(inventoryEntity.getName())
                .deleted(inventoryEntity.getDeleted())
                .folder(mapToInventoryFolderDto(inventoryEntity.getFolder()))
                .unit(mapToUnitDto(inventoryEntity.getUnit()))
                .build();
    }

    /**
     * Инвентарь Из DTO в Entity
     */
    public InventoryEntity mapToInventory(InventoryDTO inventoryDTO) {
        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setId(inventoryDTO.getId());
        inventoryEntity.setName(inventoryDTO.getName());
        inventoryEntity.setDeleted(inventoryDTO.isDeleted());
        inventoryEntity.setFolder(mapToInventoryFolder(inventoryDTO.getFolder()));
        inventoryEntity.setUnit(mapToUnit(inventoryDTO.getUnit()));
        return inventoryEntity;
    }

    /**
     * Папка инвентаря Из Entity в DTO
     */
    public InventoryFolderDTO mapToInventoryFolderDto(InventoryFolderEntity inventoryFolderEntity) {
        return InventoryFolderDTO.builder()
                .id(inventoryFolderEntity.getId())
                .deleted(inventoryFolderEntity.getDeleted())
                .name(inventoryFolderEntity.getName())
                .build();
    }

    /**
     * Папка инвентаря Из DTO в Entity
     */
    public InventoryFolderEntity mapToInventoryFolder(InventoryFolderDTO inventoryFolderDTO) {
        InventoryFolderEntity inventoryFolderEntity = new InventoryFolderEntity();
        inventoryFolderEntity.setId(inventoryFolderDTO.getId());
        inventoryFolderEntity.setName(inventoryFolderDTO.getName());
        inventoryFolderEntity.setDeleted(inventoryFolderDTO.getDeleted());
        return inventoryFolderEntity;
    }

    /**
     * Единица измерения Из Entity в DTO
     */
    public UnitDTO mapToUnitDto(UnitEntity unitEntity) {
        return UnitDTO.builder()
                .id(unitEntity.getId())
                .deleted(unitEntity.getDeleted())
                .name(unitEntity.getName())
                .build();
    }

    /**
     * Единица измерения Из DTO в Entity
     */
    public UnitEntity mapToUnit(UnitDTO unitDTO){
        UnitEntity unitEntity = new UnitEntity();
        unitEntity.setId(unitDTO.getId());
        unitEntity.setName(unitDTO.getName());
        unitEntity.setDeleted(unitDTO.getDeleted());
        return unitEntity;
    }

    /**
     * Склад Из Entity в DTO
     */
    public WarehouseDTO mapToWarehouseDto(WarehouseEntity warehouseEntity) {
        return WarehouseDTO.builder()
                .id(warehouseEntity.getId())
                .name(warehouseEntity.getName())
                .deleted(warehouseEntity.getDeleted())
                .company(warehouseEntity.getCompany())
                .person(warehouseEntity.getPerson())
                .build();
    }

    /**
     * Склад Из DTO в Entity
     */
    public WarehouseEntity mapToWarehouse(WarehouseDTO warehouseDTO) {
        WarehouseEntity warehouseEntity = new WarehouseEntity();
        warehouseEntity.setId(warehouseDTO.getId());
        warehouseEntity.setName(warehouseDTO.getName());
        warehouseEntity.setDeleted(warehouseDTO.getDeleted());
        warehouseEntity.setCompany(warehouseDTO.getCompany());
        warehouseEntity.setPerson(warehouseDTO.getPerson());
        return warehouseEntity;
    }

    /**
     * Пользователь Из Entity в DTO
     */
    public UserDTO mapToUserDto(UserEntity userEntity) {
        return UserDTO.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .deleted(userEntity.getDeleted())
                .password(userEntity.getPassword())
                .build();
    }

    /**
     * Пользователь Из DTO в Entity
     */
    public UserEntity mapToUser(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDTO.getId());
        userEntity.setName(userDTO.getName());
        userEntity.setDeleted(userDTO.getDeleted());
        userEntity.setPassword(userDTO.getPassword());
        return userEntity;
    }

    /**
     * Партнер Из Entity в DTO
     */
    public PartnerDTO mapToPartnerDto(PartnerEntity partnerEntity) {
        return PartnerDTO.builder()
                .id(partnerEntity.getId())
                .name(partnerEntity.getName())
                .deleted(partnerEntity.getDeleted())
                .build();
    }

    /**
     * Партнер Из DTO в Entity
     */
    public PartnerEntity mapToPartner(PartnerDTO partnerDTO) {
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setId(partnerDTO.getId());
        partnerEntity.setName(partnerDTO.getName());
        partnerEntity.setDeleted(partnerDTO.getDeleted());
        return partnerEntity;
    }
}
