package ru.home.inventoryaccounting.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.home.inventoryaccounting.domain.DTO.InventoryDTO;
import ru.home.inventoryaccounting.domain.entity.Inventory;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InventoryMapper implements MaperInterface<Inventory, InventoryDTO> {

    private final InventoryFolderMapper inventoryFolderMapper;
    private final UnitMapper unitMapper;

    /**
     * Преобразовать Inventory в InventoryDTO
     *
     * @param inventory - элемент Inventory
     * @return InventoryDTO
     */
    @Override
    public InventoryDTO convertToDTO(Inventory inventory) {
        return InventoryDTO.builder()
                .id(inventory.getId())
                .name(inventory.getName())
                .deleted(inventory.getDeleted())
                .folder(inventoryFolderMapper.convertToDTO(inventory.getFolder()))
                .unit(unitMapper.convertToDTO(inventory.getUnit()))
                .build();
    }

    /**
     * Преобразовать коллекцию Inventory в коллекцию InventoryDTO
     *
     * @param inventories - колекция Inventory
     * @return Collection&lt;InventoryDTO&gt;
     */
    @Override
    public Collection<InventoryDTO> convertCollectionToDTO(Collection<Inventory> inventories) {
        return inventories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}


