package ru.home.inventoryaccounting.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.home.inventoryaccounting.domain.DTO.InventoryFolderDTO;
import ru.home.inventoryaccounting.domain.entity.InventoryFolder;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InventoryFolderMapper {

    /**
     * Из Entity в DTO
     */
    public InventoryFolderDTO mapToInventoryFolderDto(InventoryFolder inventoryFolder) {
        return InventoryFolderDTO.builder()
                .id(inventoryFolder.getId())
                .deleted(inventoryFolder.getDeleted())
                .name(inventoryFolder.getName())
                .build();
    }

    /**
     * Из DTO в Entity
     */
    public InventoryFolder mapToInventoryFolder(InventoryFolderDTO inventoryFolderDTO) {
        InventoryFolder inventoryFolder = new InventoryFolder();
        inventoryFolder.setId(inventoryFolderDTO.getId());
        inventoryFolder.setName(inventoryFolderDTO.getName());
        inventoryFolder.setDeleted(inventoryFolderDTO.getDeleted());
        return inventoryFolder;
    }

    /**
     * Преобразовать коллекцию Entity в DTO
     */
    public Collection<InventoryFolderDTO> convertCollectionToDTO(Collection<InventoryFolder> inventoryFolders) {
        return inventoryFolders.stream()
                .map(this::mapToInventoryFolderDto)
                .collect(Collectors.toList());
    }
}
