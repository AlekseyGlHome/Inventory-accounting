package ru.home.inventoryaccounting.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.home.inventoryaccounting.domain.entity.InventoryFolderEntity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryFolderDto implements DtoInterface {

    private Long id;
    private Boolean isDeleted;
    private String name;

    public InventoryFolderDto(InventoryFolderEntity folderEntity) {
        setId(folderEntity.getId());
        setIsDeleted(folderEntity.getIsDeleted());
        setName(folderEntity.getName());
    }
}
