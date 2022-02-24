package ru.home.inventoryaccounting.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.home.inventoryaccounting.domain.enums.SortingDirection;

import java.time.LocalDate;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestParametersForDocuments {
    private int offset;
    private int limit;
    private String query;
    private LocalDate intervalStart;
    private LocalDate intervalEnd;
    private Long partnerId;
    private Long warehouseId;
    private Integer typeDok;
    private String[] sortColumns;
    private SortingDirection sortingDirection;
}
