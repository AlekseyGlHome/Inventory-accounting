package ru.home.inventoryaccounting.api.response;

import lombok.*;
import ru.home.inventoryaccounting.domain.DTO.WarehouseDTO;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseResponse {
    private long numberOfRecord;
    private Collection<WarehouseDTO> warehouses;
}
