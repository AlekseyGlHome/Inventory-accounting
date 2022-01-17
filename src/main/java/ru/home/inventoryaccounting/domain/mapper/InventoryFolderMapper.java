package ru.home.inventoryaccounting.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.home.inventoryaccounting.domain.DTO.InventoryFolderDTO;
import ru.home.inventoryaccounting.domain.entity.InventoryFolder;

@Component
@RequiredArgsConstructor
public class InventoryFolderMapper {

    private final ModelMapper mapper;

    public InventoryFolderDTO inventiryFolderToDTO(InventoryFolder inventoryFolder){
        return mapper.map(inventoryFolder,InventoryFolderDTO.class);
    }
}
