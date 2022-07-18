package ru.home.inventoryaccounting.util;

import ru.home.inventoryaccounting.api.request.RequestParametersForDirectories;
import ru.home.inventoryaccounting.api.request.RequestParametersForDocHeader;
import ru.home.inventoryaccounting.domain.enums.SortingDirection;

import java.time.LocalDate;

public class RequestParameterUtil {
    public static RequestParametersForDirectories getObjectOfRequestParameters(int offset, int limit,
                                                                               String query, long folderId,
                                                                               String[] sortColumns,
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

    public static RequestParametersForDirectories getObjectOfRequestParameters(int offset, int limit,
                                                                               String query,
                                                                               String[] sortColumns,
                                                                               String sortingDirection) {
        return getObjectOfRequestParameters(offset, limit, query, 0, sortColumns, sortingDirection);
    }

    public static RequestParametersForDocHeader getObjectOfRequestParametersOfDocumentHeader(
            int offset, int limit, String query, LocalDate intervalStart, LocalDate intervalEnd, Long partnerId,
            Long warehouseId, Integer typeDok, String[] sortColumns, String sortingDirection) {
        return RequestParametersForDocHeader.builder()
                .offset(offset)
                .limit(limit)
                .query(query)
                .intervalStart(intervalStart)
                .intervalEnd(intervalEnd)
                .partnerId(partnerId)
                .warehouseId(warehouseId)
                .typeDok(typeDok)
                .sortColumns(sortColumns)
                .sortingDirection(SortingDirection.valueOf(sortingDirection))
                .build();

    }

}
