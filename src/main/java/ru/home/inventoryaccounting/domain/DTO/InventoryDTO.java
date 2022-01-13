package ru.home.inventoryaccounting.domain.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryDTO {
    private long id;
    private boolean deleted;
    private String name;
    private InventoryFolderDTO folder;
    private UnitDTO unit;
}
