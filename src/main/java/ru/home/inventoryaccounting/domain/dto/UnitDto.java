package ru.home.inventoryaccounting.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.home.inventoryaccounting.domain.entity.UnitEntity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnitDto implements DtoInterface {
    private Long id;
    private Boolean isDeleted;
    private String name;

    public UnitDto(UnitEntity unitEntity) {
        setId(unitEntity.getId());
        setIsDeleted(unitEntity.getIsDeleted());
        setName(unitEntity.getName());
    }
}
