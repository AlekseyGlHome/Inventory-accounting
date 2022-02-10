package ru.home.inventoryaccounting.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WarehouseUpdateRequest {
    private long id;
    private boolean isDeleted;
    private String name;
    private String company;
    private String person;
}
