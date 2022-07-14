package ru.home.inventoryaccounting.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.home.inventoryaccounting.domain.entity.InventoryEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryDto implements DtoInterface {
    private long id;
    @JsonProperty("isDeleted")
    private boolean isDeleted;
    private String name;
    private InventoryFolderDto folder;
    private UnitDto unit;

    public InventoryDto(InventoryEntity inventoryEntity) {
        setId(inventoryEntity.getId());
        setDeleted(inventoryEntity.getIsDeleted());
        setName(inventoryEntity.getName());
        setFolder(new InventoryFolderDto(inventoryEntity.getFolder()));
        setUnit(new UnitDto(inventoryEntity.getUnit()));

    }
}
