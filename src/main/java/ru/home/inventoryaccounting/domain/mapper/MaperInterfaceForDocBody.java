package ru.home.inventoryaccounting.domain.mapper;

import ru.home.inventoryaccounting.domain.DTO.DocumentHeaderDTO;

import java.util.Collection;

/**
 * Интерфейс Преобразователь Entity в DTO
 * &lt;T Entity&gt - &lt;T1 DTO&gt
 */
public interface MaperInterfaceForDocBody<T, T1> {


    T1 convertToDTO(T element, DocumentHeaderDTO documentHeaderDTO);

    Collection<T1> convertCollectionToDTO(Collection<T> collection, DocumentHeaderDTO documentHeaderDTO);
}
