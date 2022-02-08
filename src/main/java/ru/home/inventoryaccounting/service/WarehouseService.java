package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.response.DTOResponse;
import ru.home.inventoryaccounting.domain.dto.WarehouseDto;
import ru.home.inventoryaccounting.domain.entity.WarehouseEntity;
import ru.home.inventoryaccounting.domain.mapper.MapperUtiliti;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.WarehouseRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final MapperUtiliti mapperUtiliti;
    private final WarehouseRepository warehouseRepository;

    /**
     * выбор склада по идентификатору
     *
     * @param id - идентификатор склада
     * @return Warehouse
     * @throws NotFoundException
     */
    public WarehouseDto findById(long id) throws NotFoundException {
        Optional<WarehouseEntity> warehouse = warehouseRepository.findById(id);
        return warehouse.map(mapperUtiliti::mapToWarehouseDto)
                .orElseThrow(() -> new NotFoundException("Слад с Id: " + id + " не найден."));
    }

    /**
     * выбор склада по вхождению в наименование
     *
     * @param offset - номер страницы
     * @param limit  - количество элементов на странице
     * @param query  - строка поиска
     * @return DTOResponse&lt;WarehouseDTO&gt;
     * @throws InvalidRequestParameteException
     */
    public DTOResponse<WarehouseDto> findByQueryString(int offset, int limit,
                                                       String query) throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<WarehouseEntity> warehouses;
        if (!query.isEmpty() || !query.isBlank()) {
            warehouses = warehouseRepository.findByNameLike(pageRequest, query);
        } else {
            throw new InvalidRequestParameteException("Неверный параметр запроса");
        }
        return new DTOResponse<>(warehouses.getTotalElements(),
                mapperUtiliti.mapToCollectionWarehouseDto(warehouses.getContent()));
    }

    /**
     * выбор всех складов
     *
     * @param offset - номер страницы
     * @param limit  - количество элементов на странице
     * @return DTOResponse&lt;WarehouseDTO&gt;
     */
    public DTOResponse<WarehouseDto> findAll(int offset, int limit) {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<WarehouseEntity> warehouses;
        warehouses = warehouseRepository.findAll(pageRequest);
        return new DTOResponse<>(warehouses.getTotalElements(),
                mapperUtiliti.mapToCollectionWarehouseDto(warehouses.getContent()));
    }

    // создать страницу пагинации
    private PageRequest getPageRequest(int offset, int limit) {
        int numberPage = offset / limit;

        return PageRequest.of(numberPage, limit);
    }
}
