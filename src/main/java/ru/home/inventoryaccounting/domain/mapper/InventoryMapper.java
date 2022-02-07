package ru.home.inventoryaccounting.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.home.inventoryaccounting.domain.DTO.InventoryDTO;
import ru.home.inventoryaccounting.domain.entity.InventoryEntity;

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
    public InventoryDTO mapToInventoryDto(InventoryEntity inventoryEntity) {
        return InventoryDTO.builder()
                .id(inventoryEntity.getId())
                .name(inventoryEntity.getName())
                .deleted(inventoryEntity.getDeleted())
                .folder(inventoryFolderMapper.mapToInventoryFolderDto(inventoryEntity.getFolder()))
                .unit(unitMapper.mapToUnitDto(inventoryEntity.getUnit()))
                .build();
    }

    /**
     * Из DTO в Entity
     */
    public InventoryEntity mapToInventory(InventoryDTO inventoryDTO) {
        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setId(inventoryDTO.getId());
        inventoryEntity.setName(inventoryDTO.getName());
        inventoryEntity.setDeleted(inventoryDTO.isDeleted());
        inventoryEntity.setFolder(inventoryFolderMapper.mapToInventoryFolder(inventoryDTO.getFolder()));
        inventoryEntity.setUnit(unitMapper.mapToUnit(inventoryDTO.getUnit()));
        return inventoryEntity;
    }

    /**
     * Преобразовать коллекцию Entity в DTO
     */
    public Collection<InventoryDTO> convertCollectionToDTO(Collection<InventoryEntity> inventories) {
        return inventories.stream()
                .map(this::mapToInventoryDto)
                .collect(Collectors.toList());
    }

}


