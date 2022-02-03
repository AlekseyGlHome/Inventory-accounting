package ru.home.inventoryaccounting.domain.mapper;


import ru.home.inventoryaccounting.domain.DTO.*;
import ru.home.inventoryaccounting.domain.entity.*;

public class MapperUtiliti {

    /**
     * Инвентарь Из Entity в DTO
     */
    public InventoryDTO mapToInventoryDto(Inventory inventory) {
        return InventoryDTO.builder()
                .id(inventory.getId())
                .name(inventory.getName())
                .deleted(inventory.getDeleted())
                .folder(mapToInventoryFolderDto(inventory.getFolder()))
                .unit(mapToUnitDto(inventory.getUnit()))
                .build();
    }

    /**
     * Инвентарь Из DTO в Entity
     */
    public Inventory mapToInventory(InventoryDTO inventoryDTO) {
        Inventory inventory = new Inventory();
        inventory.setId(inventoryDTO.getId());
        inventory.setName(inventoryDTO.getName());
        inventory.setDeleted(inventoryDTO.isDeleted());
        inventory.setFolder(mapToInventoryFolder(inventoryDTO.getFolder()));
        inventory.setUnit(mapToUnit(inventoryDTO.getUnit()));
        return inventory;
    }

    /**
     * Папка инвентаря Из Entity в DTO
     */
    public InventoryFolderDTO mapToInventoryFolderDto(InventoryFolder inventoryFolder) {
        return InventoryFolderDTO.builder()
                .id(inventoryFolder.getId())
                .deleted(inventoryFolder.getDeleted())
                .name(inventoryFolder.getName())
                .build();
    }

    /**
     * Папка инвентаря Из DTO в Entity
     */
    public InventoryFolder mapToInventoryFolder(InventoryFolderDTO inventoryFolderDTO) {
        InventoryFolder inventoryFolder = new InventoryFolder();
        inventoryFolder.setId(inventoryFolderDTO.getId());
        inventoryFolder.setName(inventoryFolderDTO.getName());
        inventoryFolder.setDeleted(inventoryFolderDTO.getDeleted());
        return inventoryFolder;
    }

    /**
     * Единица измерения Из Entity в DTO
     */
    public UnitDTO mapToUnitDto(Unit unit) {
        return UnitDTO.builder()
                .id(unit.getId())
                .deleted(unit.getDeleted())
                .name(unit.getName())
                .build();
    }

    /**
     * Единица измерения Из DTO в Entity
     */
    public Unit mapToUnit(UnitDTO unitDTO){
        Unit unit = new Unit();
        unit.setId(unitDTO.getId());
        unit.setName(unitDTO.getName());
        unit.setDeleted(unitDTO.getDeleted());
        return unit;
    }

    /**
     * Склад Из Entity в DTO
     */
    public WarehouseDTO mapToWarehouseDto(Warehouse warehouse) {
        return WarehouseDTO.builder()
                .id(warehouse.getId())
                .name(warehouse.getName())
                .deleted(warehouse.getDeleted())
                .company(warehouse.getCompany())
                .person(warehouse.getPerson())
                .build();
    }

    /**
     * Склад Из DTO в Entity
     */
    public Warehouse mapToWarehouse(WarehouseDTO warehouseDTO) {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(warehouseDTO.getId());
        warehouse.setName(warehouseDTO.getName());
        warehouse.setDeleted(warehouseDTO.getDeleted());
        warehouse.setCompany(warehouseDTO.getCompany());
        warehouse.setPerson(warehouseDTO.getPerson());
        return warehouse;
    }

    /**
     * Пользователь Из Entity в DTO
     */
    public UserDTO mapToUserDto(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .deleted(user.getDeleted())
                .password(user.getPassword())
                .build();
    }

    /**
     * Пользователь Из DTO в Entity
     */
    public User mapToUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setDeleted(userDTO.getDeleted());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    /**
     * Партнер Из Entity в DTO
     */
    public PartnerDTO mapToPartnerDto(Partner partner) {
        return PartnerDTO.builder()
                .id(partner.getId())
                .name(partner.getName())
                .deleted(partner.getDeleted())
                .build();
    }

    /**
     * Партнер Из DTO в Entity
     */
    public Partner mapToPartner(PartnerDTO partnerDTO) {
        Partner partner = new Partner();
        partner.setId(partnerDTO.getId());
        partner.setName(partnerDTO.getName());
        partner.setDeleted(partnerDTO.getDeleted());
        return partner;
    }
}
