package ru.home.inventoryaccounting.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.home.inventoryaccounting.domain.DTO.WarehouseDTO;
import ru.home.inventoryaccounting.domain.entity.WarehouseEntity;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WarehouseMapper implements MaperInterface<WarehouseEntity, WarehouseDTO> {

    /**
     * Преобразовать Warehouse в WarehouseDTO
     *
     * @param warehouseEntity - элемент Warehouse
     * @return WarehouseDTO
     */
    @Override
    public WarehouseDTO convertToDTO(WarehouseEntity warehouseEntity) {
        return WarehouseDTO.builder()
                .id(warehouseEntity.getId())
                .name(warehouseEntity.getName())
                .deleted(warehouseEntity.getDeleted())
                .company(warehouseEntity.getCompany())
                .person(warehouseEntity.getPerson())
                .build();
    }


    /**
     * Преобразовать коллекцию Warehouse в коллекцию WarehouseDTO
     *
     * @param warehouseEntities - колекция Warehouse
     * @return Collection&lt;WarehouseDTO&gt;
     */
    @Override
    public Collection<WarehouseDTO> convertCollectionToDTO(Collection<WarehouseEntity> warehouseEntities) {
        return warehouseEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
