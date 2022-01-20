package ru.home.inventoryaccounting.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.home.inventoryaccounting.domain.DTO.InventoryFolderDTO;
import ru.home.inventoryaccounting.domain.entity.InventoryFolder;

@Component
@RequiredArgsConstructor
public class InventoryFolderMapper {

    public InventoryFolderDTO inventoryFolderToDTO(InventoryFolder inventoryFolder) {
        return InventoryFolderDTO.builder()
                .id(inventoryFolder.getId())
                .deleted(inventoryFolder.getDeleted())
                .name(inventoryFolder.getName())
                .build();
    }
}
