package ru.home.inventoryaccounting.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.home.inventoryaccounting.domain.DTO.WarehouseDTO;
import ru.home.inventoryaccounting.domain.entity.Warehouse;

@Component
@RequiredArgsConstructor
public class WarehouseMapper {

    public WarehouseDTO warehouseToDTO(Warehouse warehouse) {
        return WarehouseDTO.builder()
                .id(warehouse.getId())
                .name(warehouse.getName())
                .deleted(warehouse.getDeleted())
                .company(warehouse.getCompany())
                .person(warehouse.getPerson())
                .build();
    }
}
