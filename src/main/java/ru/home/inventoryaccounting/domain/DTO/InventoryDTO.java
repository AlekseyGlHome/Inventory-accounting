package ru.home.inventoryaccounting.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryDTO {
    private long id;
    private boolean deleted;
    private String name;
    private InventoryFolderDTO folder;
    private UnitDTO unit;
}
