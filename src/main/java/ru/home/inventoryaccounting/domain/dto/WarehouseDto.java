package ru.home.inventoryaccounting.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.home.inventoryaccounting.domain.entity.WarehouseEntity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseDto implements DtoInterface {
    private Long id;
    private String company;
    private Boolean isDeleted;
    private String name;
    private String person;

    public WarehouseDto(WarehouseEntity warehouseEntity) {
        setId(warehouseEntity.getId());
        setCompany(warehouseEntity.getCompany());
        setIsDeleted(warehouseEntity.getIsDeleted());
        setName(warehouseEntity.getName());
        setPerson(warehouseEntity.getPerson());
    }
}
