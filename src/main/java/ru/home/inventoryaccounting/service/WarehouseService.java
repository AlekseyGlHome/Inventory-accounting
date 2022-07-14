package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.request.RequestParametersForDirectories;
import ru.home.inventoryaccounting.api.request.WarehouseRequest;
import ru.home.inventoryaccounting.api.response.DtoResponse;
import ru.home.inventoryaccounting.domain.dto.WarehouseDto;
import ru.home.inventoryaccounting.domain.entity.WarehouseEntity;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.WarehouseRepository;
import ru.home.inventoryaccounting.util.PageRequestUtil;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    private final String MESSAGE_NOT_FOUND = "Склад с Id: %s не найден.";
    private final String MESSAGE_BAD_REQUESR = "Неверный параметр запроса";

    /**
     * выбор склада по идентификатору
     */
    public WarehouseEntity findById(long id) {
        Optional<WarehouseEntity> warehouse = warehouseRepository.findById(id);
        return warehouse
                .orElseThrow(() -> new NotFoundException(String.format(MESSAGE_NOT_FOUND, id)));
    }

    /**
     * выбор склада по вхождению в наименование
     */
    public DtoResponse<WarehouseDto> findByNameLike(RequestParametersForDirectories request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);
        Page<WarehouseEntity> warehouses;
        if (!request.getQuery().isEmpty() || !request.getQuery().isBlank()) {
            warehouses = warehouseRepository.findByNameLike(pageRequest, request.getQuery());
        } else {
            throw new InvalidRequestParameteException(MESSAGE_BAD_REQUESR);
        }
        return new DtoResponse<>(warehouses.getTotalElements(),
                warehouses.getContent().stream().map(WarehouseDto::new).collect(Collectors.toList()));
    }

    /**
     * выбор всех складов
     */
    public DtoResponse<WarehouseDto> findAll(RequestParametersForDirectories request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);
        Page<WarehouseEntity> warehouses;
        warehouses = warehouseRepository.findAll(pageRequest);
        return new DtoResponse<>(warehouses.getTotalElements(),
                warehouses.getContent().stream().map(WarehouseDto::new).collect(Collectors.toList()));
    }

    /**
     * общий запрос
     */
    public DtoResponse<WarehouseDto> selectQuery(RequestParametersForDirectories request) {
        if (!request.getQuery().isEmpty() || !request.getQuery().isBlank()) {
            return findByNameLike(request);
        }
        return findAll(request);
    }

    // добавить карточку
    public WarehouseDto add(WarehouseRequest request){
        WarehouseEntity warehouseEntity = fillInventory(new WarehouseEntity(), request);
        return new WarehouseDto(warehouseRepository.save(warehouseEntity));
    }

    // обновить карточку
    public WarehouseDto update(long id, WarehouseRequest request) {
        WarehouseEntity warehouseEntity = fillInventory(findById(id), request);
        return new WarehouseDto(warehouseRepository.save(warehouseEntity));
    }

    // заполнить карточку из запросса
    private WarehouseEntity fillInventory(WarehouseEntity warehouseEntity, WarehouseRequest request) {
        warehouseEntity.setName(request.getName());
        warehouseEntity.setIsDeleted(request.isDeleted());
        warehouseEntity.setCompany(request.getCompany());
        warehouseEntity.setPerson(request.getPerson());
        return warehouseEntity;
    }

    /**
     * удалить (переменную is_deleted в true)
     */
    public void deleteById(long id) {
        if (warehouseRepository.updateIsDeleteToTrueById(id) <= 0) {
            throw new NotFoundException(String.format(MESSAGE_NOT_FOUND, id));
        }
    }


    public DtoResponse<WarehouseDto> getDeleted(RequestParametersForDirectories request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);
        Page<WarehouseEntity> warehousesEntities;
        warehousesEntities = warehouseRepository.getByIsDeletedTrue(pageRequest);
        return new DtoResponse<>(warehousesEntities.getTotalElements(),
                warehousesEntities.getContent().stream().map(WarehouseDto::new).collect(Collectors.toList()));
    }
}
