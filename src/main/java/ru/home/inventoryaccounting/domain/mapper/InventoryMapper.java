package ru.home.inventoryaccounting.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.home.inventoryaccounting.domain.DTO.InventoryDTO;
import ru.home.inventoryaccounting.domain.entity.Inventory;

@Component
@RequiredArgsConstructor
public class InventoryMapper {

    private final ModelMapper mapper;

    public InventoryDTO InventoryToDTO(Inventory inventory){
        return mapper.map(inventory, InventoryDTO.class);
    }
}
