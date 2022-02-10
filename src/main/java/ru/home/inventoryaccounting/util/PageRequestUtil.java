package ru.home.inventoryaccounting.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.home.inventoryaccounting.api.request.ParameterRequest;
import ru.home.inventoryaccounting.domain.enums.SortingDirection;

public class PageRequestUtil {

    /**
     * создать страницу пагинации
     *
     */
    public static PageRequest getPageToRequest(ParameterRequest request) {
        int numberPage = request.getOffset() / request.getLimit();

        if (request.getSortingDirection() == SortingDirection.ASC) {
            return PageRequest.of(numberPage, request.getLimit(), Sort.by(request.getSortColumns()).ascending());
        }
        return PageRequest.of(numberPage, request.getLimit(), Sort.by(request.getSortColumns()).descending());

    }
}
