package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.response.DTOResponse;
import ru.home.inventoryaccounting.domain.DTO.WarehouseDTO;
import ru.home.inventoryaccounting.domain.entity.Warehouse;
import ru.home.inventoryaccounting.domain.mapper.WarehouseMapper;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.WarehouseRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseMapper warehouseMapper;
    private final WarehouseRepository warehouseRepository;

    /**
     * выбор склада по идентификатору
     *
     * @param id - идентификатор склада
     * @return WarehouseDTO
     * @throws NotFoundException
     */
    public WarehouseDTO findById(long id) throws NotFoundException {
        Optional<Warehouse> warehouse = warehouseRepository.findById(id);
        return warehouse.map(warehouseMapper::warehouseToDTO).orElseThrow(() -> new NotFoundException("Запись с Id: " + id + " не найдена."));
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
    public DTOResponse<WarehouseDTO> findByQueryString(int offset, int limit, String query) throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<Warehouse> warehouses;
        if (!query.isEmpty() || !query.isBlank()) {
            warehouses = warehouseRepository.findByNameLike(pageRequest, query);
        } else {
            throw new InvalidRequestParameteException("Неверный параметр запроса");
        }
        return new DTOResponse<>(warehouses.getTotalElements(), getWarehouseDTOS(warehouses));
    }

    /**
     * выбор всех складов
     *
     * @param offset - номер страницы
     * @param limit  - количество элементов на странице
     * @return DTOResponse&lt;WarehouseDTO&gt;
     */
    public DTOResponse<WarehouseDTO> findAll(int offset, int limit) {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<Warehouse> warehouses;
        warehouses = warehouseRepository.findAll(pageRequest);
        return new DTOResponse<>(warehouses.getTotalElements(), getWarehouseDTOS(warehouses));
    }

    //Преобразовать List<Warehouse> в List<WarehouseDTO>
    private List<WarehouseDTO> getWarehouseDTOS(Page<Warehouse> warehouse) {
        return warehouse.getContent().stream()
                .map(warehouseMapper::warehouseToDTO)
                .collect(Collectors.toList());
    }

    // создать страницу пагинации
    private PageRequest getPageRequest(int offset, int limit) {
        int numberPage = offset / limit;

        return PageRequest.of(numberPage, limit);
    }
}
