package ru.home.inventoryaccounting.domain.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WarehouseDTO {
    private Long id;
    private String company;
    private Boolean deleted;
    private String name;
    private String person;
}
