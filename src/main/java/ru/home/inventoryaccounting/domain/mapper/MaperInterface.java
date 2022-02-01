package ru.home.inventoryaccounting.domain.mapper;

import java.util.Collection;

/**
 * Интерфейс Преобразователь Entity в DTO
 *&lt;T Entity&gt - &lt;T1 DTO&gt
 */
public interface MaperInterface<T,T1> {

    T1 convertToDTO(T element);

    Collection<T1> convertCollectionToDTO(Collection<T> collection);
}
