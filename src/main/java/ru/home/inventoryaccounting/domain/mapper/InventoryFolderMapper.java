package ru.home.inventoryaccounting.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.home.inventoryaccounting.domain.DTO.InventoryFolderDTO;
import ru.home.inventoryaccounting.domain.entity.InventoryFolder;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InventoryFolderMapper implements MaperInterface<InventoryFolder, InventoryFolderDTO> {

    /**
     * Преобразовать DocumentBody в DocumentBodyDTO
     *
     * @param inventoryFolder - элемент DocumentBody
     * @return InventoryFolderDTO
     */
    @Override
    public InventoryFolderDTO convertToDTO(InventoryFolder inventoryFolder) {
        return InventoryFolderDTO.builder()
                .id(inventoryFolder.getId())
                .deleted(inventoryFolder.getDeleted())
                .name(inventoryFolder.getName())
                .build();
    }

    /**
     * Преобразовать коллекцию InventoryFolder в коллекцию InventoryFolderDTO
     *
     * @param inventoryFolders - колекция DocumentBody
     * @return Collection&lt;DocumentBodyDTO&gt;
     */
    @Override
    public Collection<InventoryFolderDTO> convertCollectionToDTO(Collection<InventoryFolder> inventoryFolders) {
        return inventoryFolders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
