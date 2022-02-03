package ru.home.inventoryaccounting.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.home.inventoryaccounting.domain.DTO.InventoryDTO;
import ru.home.inventoryaccounting.domain.entity.Inventory;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InventoryMapper {

    private final InventoryFolderMapper inventoryFolderMapper;
    private final UnitMapper unitMapper;

    /**
     * Из Entity в DTO
     */
    public InventoryDTO mapToInventoryDto(Inventory inventory) {
        return InventoryDTO.builder()
                .id(inventory.getId())
                .name(inventory.getName())
                .deleted(inventory.getDeleted())
                .folder(inventoryFolderMapper.mapToInventoryFolderDto(inventory.getFolder()))
                .unit(unitMapper.mapToUnitDto(inventory.getUnit()))
                .build();
    }

    /**
     * Из DTO в Entity
     */
    public Inventory mapToInventory(InventoryDTO inventoryDTO) {
        Inventory inventory = new Inventory();
        inventory.setId(inventoryDTO.getId());
        inventory.setName(inventoryDTO.getName());
        inventory.setDeleted(inventoryDTO.isDeleted());
        inventory.setFolder(inventoryFolderMapper.mapToInventoryFolder(inventoryDTO.getFolder()));
        inventory.setUnit(unitMapper.mapToUnit(inventoryDTO.getUnit()));
        return inventory;
    }

    /**
     * Преобразовать коллекцию Entity в DTO
     */
    public Collection<InventoryDTO> convertCollectionToDTO(Collection<Inventory> inventories) {
        return inventories.stream()
                .map(this::mapToInventoryDto)
                .collect(Collectors.toList());
    }

}


