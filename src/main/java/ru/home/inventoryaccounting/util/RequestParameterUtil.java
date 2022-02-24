package ru.home.inventoryaccounting.util;

import ru.home.inventoryaccounting.api.request.RequestParametersForDirectories;
import ru.home.inventoryaccounting.domain.enums.SortingDirection;

public class RequestParameterUtil {
    public static RequestParametersForDirectories getObjectOfRequestParameters(int offset, int limit, String query, long folderId, String[] sortColumns,
                                                                               String sortingDirection) {
        return RequestParametersForDirectories.builder()
                .offset(offset)
                .limit(limit)
                .query(query)
                .folderId(folderId)
                .sortColumns(sortColumns)
                .sortingDirection(SortingDirection.valueOf(sortingDirection))
                .build();
    }

    public static RequestParametersForDirectories getObjectOfRequestParameters(int offset, int limit, String query, String[] sortColumns, String sortingDirection) {
        return getObjectOfRequestParameters(offset, limit, query, 0, sortColumns, sortingDirection);
    }
}
