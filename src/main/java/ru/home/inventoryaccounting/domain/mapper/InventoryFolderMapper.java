package ru.home.inventoryaccounting.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.home.inventoryaccounting.domain.DTO.InventoryFolderDTO;
import ru.home.inventoryaccounting.domain.entity.InventoryFolderEntity;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InventoryFolderMapper {

    /**
     * Из Entity в DTO
     */
    public InventoryFolderDTO mapToInventoryFolderDto(InventoryFolderEntity inventoryFolderEntity) {
        return InventoryFolderDTO.builder()
                .id(inventoryFolderEntity.getId())
                .deleted(inventoryFolderEntity.getDeleted())
                .name(inventoryFolderEntity.getName())
                .build();
    }

    /**
     * Из DTO в Entity
     */
    public InventoryFolderEntity mapToInventoryFolder(InventoryFolderDTO inventoryFolderDTO) {
        InventoryFolderEntity inventoryFolderEntity = new InventoryFolderEntity();
        inventoryFolderEntity.setId(inventoryFolderDTO.getId());
        inventoryFolderEntity.setName(inventoryFolderDTO.getName());
        inventoryFolderEntity.setDeleted(inventoryFolderDTO.getDeleted());
        return inventoryFolderEntity;
    }

    /**
     * Преобразовать коллекцию Entity в DTO
     */
    public Collection<InventoryFolderDTO> convertCollectionToDTO(Collection<InventoryFolderEntity> inventoryFolderEntities) {
        return inventoryFolderEntities.stream()
                .map(this::mapToInventoryFolderDto)
                .collect(Collectors.toList());
    }
}
