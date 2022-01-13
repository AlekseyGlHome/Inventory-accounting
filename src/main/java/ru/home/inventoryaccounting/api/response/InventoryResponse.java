package ru.home.inventoryaccounting.api.response;

import lombok.*;
import ru.home.inventoryaccounting.domain.DTO.InventoryDTO;
import ru.home.inventoryaccounting.domain.entity.Inventory;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryResponse {
    private long numberOfRecord;
    private Collection<InventoryDTO> inventories;
}
