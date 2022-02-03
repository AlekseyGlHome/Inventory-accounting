package ru.home.inventoryaccounting.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.home.inventoryaccounting.domain.enums.SortingDirection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParameterRequest {
    private int offset;
    private int limit;
    private String query;
    private long folderId;
    private String[] sortColumns;
    private SortingDirection sortingDirection;
}
