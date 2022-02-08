package ru.home.inventoryaccounting.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryDto implements DtoInterface {
    private long id;
    private boolean deleted;
    private String name;
    private InventoryFolderDto folder;
    private UnitDto unit;
}
