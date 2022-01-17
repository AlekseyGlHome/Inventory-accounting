package ru.home.inventoryaccounting.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.home.inventoryaccounting.domain.DTO.WarehouseDTO;
import ru.home.inventoryaccounting.domain.entity.Warehouse;

@Component
@RequiredArgsConstructor
public class WarehouseMapper {
    private final ModelMapper mapper;

    public WarehouseDTO warehouseToDTO(Warehouse warehouse){
        return mapper.map(warehouse,WarehouseDTO.class);
    }
}
