package ru.home.inventoryaccounting.util;

import ru.home.inventoryaccounting.api.request.ParameterRequest;
import ru.home.inventoryaccounting.domain.enums.SortingDirection;

public class RequestParameterUtil {
    public static ParameterRequest getObjectOfRequestParameters(int offset, int limit, String query, long folderId, String sortColumns,
                                                                String sortingDirection) {
        return ParameterRequest.builder()
                .offset(offset)
                .limit(limit)
                .query(query)
                .folderId(folderId)
                .sortColumns(sortColumns.split(", +"))
                .sortingDirection(SortingDirection.valueOf(sortingDirection))
                .build();
    }

    public static ParameterRequest getObjectOfRequestParameters(int offset, int limit, String query, String sortColumns, String sortingDirection) {
        return getObjectOfRequestParameters(offset, limit, query, 0, sortColumns, sortingDirection);
    }
}
