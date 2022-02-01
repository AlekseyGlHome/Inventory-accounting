package ru.home.inventoryaccounting.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.home.inventoryaccounting.domain.DTO.WarehouseDTO;
import ru.home.inventoryaccounting.domain.entity.Warehouse;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WarehouseMapper implements MaperInterface<Warehouse, WarehouseDTO> {

    /**
     * Преобразовать Warehouse в WarehouseDTO
     *
     * @param warehouse - элемент Warehouse
     * @return WarehouseDTO
     */
    @Override
    public WarehouseDTO convertToDTO(Warehouse warehouse) {
        return WarehouseDTO.builder()
                .id(warehouse.getId())
                .name(warehouse.getName())
                .deleted(warehouse.getDeleted())
                .company(warehouse.getCompany())
                .person(warehouse.getPerson())
                .build();
    }


    /**
     * Преобразовать коллекцию Warehouse в коллекцию WarehouseDTO
     *
     * @param warehouses - колекция Warehouse
     * @return Collection&lt;WarehouseDTO&gt;
     */
    @Override
    public Collection<WarehouseDTO> convertCollectionToDTO(Collection<Warehouse> warehouses) {
        return warehouses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
