package ru.home.inventoryaccounting.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WarehouseDto implements DtoInterface {
    private Long id;
    private String company;
    private Boolean isDeleted;
    private String name;
    private String person;
}
