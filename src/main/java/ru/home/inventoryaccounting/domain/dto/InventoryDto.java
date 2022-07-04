package ru.home.inventoryaccounting.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("isDeleted")
    private boolean isDeleted;
    private String name;
    private InventoryFolderDto folder;
    private UnitDto unit;
}
