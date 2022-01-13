package ru.home.inventoryaccounting.domain.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnitDTO {
    private Long id;
    private Boolean deleted;
    private String name;
}
