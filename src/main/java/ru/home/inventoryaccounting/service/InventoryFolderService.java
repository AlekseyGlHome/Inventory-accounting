package ru.home.inventoryaccounting.service;

import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.domain.DTO.InventoryFolderDTO;
import ru.home.inventoryaccounting.domain.entity.InventoryFolder;

@Service
public class InventoryFolderService {


    public InventoryFolderDTO buildInventoryFolderDTO(InventoryFolder inventoryFolder) {
        return InventoryFolderDTO.builder().id(inventoryFolder.getId())
                .deleted(inventoryFolder.getDeleted())
                .name(inventoryFolder.getName())
                .build();
    }
}
