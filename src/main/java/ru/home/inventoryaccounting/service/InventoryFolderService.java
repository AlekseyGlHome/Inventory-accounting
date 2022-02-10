package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.request.InventoryFolderUpdateRequest;
import ru.home.inventoryaccounting.api.request.ParameterRequest;
import ru.home.inventoryaccounting.api.response.DtoResponse;
import ru.home.inventoryaccounting.domain.dto.InventoryFolderDto;
import ru.home.inventoryaccounting.domain.entity.InventoryFolderEntity;
import ru.home.inventoryaccounting.domain.mapper.MapperUtiliti;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.InventoryFolderRepository;
import ru.home.inventoryaccounting.util.PageRequestUtil;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryFolderService {

    private final String MESSAGE_NOT_FOUND = "Папка инвентаря с Id: %s не найдена.";
    private final String MESSAGE_BAD_REQUESR = "Неверный параметр запроса";

    private final MapperUtiliti mapperUtiliti;
    private final InventoryFolderRepository inventoryFolderRepository;


    /**
     * выбрать папку по идентификатору
     */
    public InventoryFolderDto findById(long id) throws NotFoundException {
        Optional<InventoryFolderEntity> inventoryFolder = inventoryFolderRepository.findById(id);
        return inventoryFolder.map(mapperUtiliti::mapToInventoryFolderDto)
                .orElseThrow(() -> new NotFoundException(String.format(MESSAGE_NOT_FOUND, id)));
    }

    /**
     * выбрать папки по входждению в наименование
     */
    public DtoResponse<InventoryFolderDto> findByNameLike(ParameterRequest request) throws InvalidRequestParameteException {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);
        Page<InventoryFolderEntity> inventoryFolders;
        if (!request.getQuery().isEmpty() || !request.getQuery().isBlank()) {
            inventoryFolders = inventoryFolderRepository.findByNameLike(pageRequest, request.getQuery());
        } else {
            throw new InvalidRequestParameteException(MESSAGE_BAD_REQUESR);
        }
        return new DtoResponse<>(true, "", inventoryFolders.getTotalElements(),
                mapperUtiliti.mapToCollectionInventoryFolderDto(inventoryFolders.getContent()));
    }

    /**
     * выбрать весе папки
     */
    public DtoResponse<InventoryFolderDto> findAll(ParameterRequest request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);
        Page<InventoryFolderEntity> inventoryFolders;
        inventoryFolders = inventoryFolderRepository.findAll(pageRequest);
        return new DtoResponse<>(true, "", inventoryFolders.getTotalElements(),
                mapperUtiliti.mapToCollectionInventoryFolderDto(inventoryFolders.getContent()));
    }


    /**
     * общий запрос
     */
    public DtoResponse<InventoryFolderDto> selectQuery(ParameterRequest request) throws InvalidRequestParameteException {
        if (!request.getQuery().isEmpty() || !request.getQuery().isBlank()) {
            return findByNameLike(request);
        }
        return findAll(request);
    }


    // добавить карточку
    public InventoryFolderDto add(InventoryFolderUpdateRequest request) throws NotFoundException {
        InventoryFolderEntity inventoryFolderEntity = fillInventory(new InventoryFolderEntity(), request);
        return mapperUtiliti.mapToInventoryFolderDto(inventoryFolderRepository.save(inventoryFolderEntity));
    }

    // обновить карточку
    public InventoryFolderDto update(long id, InventoryFolderUpdateRequest request) throws NotFoundException {
        InventoryFolderEntity inventoryFolderEntity = fillInventory(mapperUtiliti.mapToInventoryFolderEntity(findById(id)), request);
        return mapperUtiliti.mapToInventoryFolderDto(inventoryFolderRepository.save(inventoryFolderEntity));
    }

    // заполнить карточку из запросса
    private InventoryFolderEntity fillInventory(InventoryFolderEntity inventoryFolderEntity, InventoryFolderUpdateRequest request) {
        inventoryFolderEntity.setName(request.getName());
        inventoryFolderEntity.setIsDeleted(request.isDeleted());
        return inventoryFolderEntity;
    }

    /**
     * удалить (переменную is_deleted в true)
     */
    public void deleteById(long id) throws NotFoundException {
        if (inventoryFolderRepository.updateIsDeleteToTrueById(id) <= 0) {
            throw new NotFoundException(String.format(MESSAGE_NOT_FOUND, id));
        }
    }

}
