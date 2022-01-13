package ru.home.inventoryaccounting.domain.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryFolderDTO {

    private Long id;
    private Boolean deleted;
    private String name;
}
