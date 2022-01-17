package ru.home.inventoryaccounting.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryFolderDTO {

    private Long id;
    private Boolean deleted;
    private String name;
}
