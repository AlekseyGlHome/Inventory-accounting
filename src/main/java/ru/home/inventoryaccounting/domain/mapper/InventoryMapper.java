package ru.home.inventoryaccounting.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.home.inventoryaccounting.domain.DTO.InventoryDTO;
import ru.home.inventoryaccounting.domain.entity.Inventory;

@Component
@RequiredArgsConstructor
public class InventoryMapper {

    private final InventoryFolderMapper inventoryFolderMapper;
    private final UnitMapper unitMapper;

    public InventoryDTO InventoryToDTO(Inventory inventory) {
        return InventoryDTO.builder()
                .id(inventory.getId())
                .name(inventory.getName())
                .deleted(inventory.getDeleted())
                .folder(inventoryFolderMapper.inventiryFolderToDTO(inventory.getFolder()))
                .unit(unitMapper.unitToDTO(inventory.getUnit()))
                .build();
    }
}


