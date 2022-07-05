package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.request.InventoryRequest;
import ru.home.inventoryaccounting.api.request.RequestParametersForDirectories;
import ru.home.inventoryaccounting.api.response.DtoResponse;
import ru.home.inventoryaccounting.domain.dto.InventoryDto;
import ru.home.inventoryaccounting.domain.entity.InventoryEntity;
import ru.home.inventoryaccounting.domain.mapper.MapperUtiliti;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.InventoryRepository;
import ru.home.inventoryaccounting.util.PageRequestUtil;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final MapperUtiliti mapperUtiliti;
    private final InventoryRepository inventoryRepository;
    private final InventoryFolderService inventoryFolderService;
    private final UnitService unitService;

    private final String MESSAGE_NOT_FOUND = "Инвентарь с Id: %s не найдена.";
    private final String MESSAGE_BAD_REQUESR = "Неверный параметр запроса";

    /**
     * выбрать инвентарь по идентификатору
     * @param id
     * @return 
     */
    public InventoryDto findById(long id) {
        Optional<InventoryEntity> inventory = inventoryRepository.findById(id);
        return inventory.map(mapperUtiliti::mapToInventoryDto)
                .orElseThrow(() -> new NotFoundException(String.format(MESSAGE_NOT_FOUND, id)));
    }

    /**
     * удалить (переменную is_deleted в true)
     * @param id
     */
    public void deleteById(long id) {
        int countDelete = inventoryRepository.updateIsDeleteToTrueById(id);
        if (countDelete <= 0) {
            throw new NotFoundException(String.format(MESSAGE_NOT_FOUND, id));
        }
    }

    /**
     * выбрать инвентарь по входждению в наименование
     * @param request
     * @return 
     */
    public DtoResponse<InventoryDto> findByNameLike(RequestParametersForDirectories request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);
        Page<InventoryEntity> inventories;
        if (!request.getQuery().isEmpty() || !request.getQuery().isBlank()) {
            inventories = inventoryRepository.findByNameLike(pageRequest, request.getQuery());
        } else {
            throw new InvalidRequestParameteException(MESSAGE_BAD_REQUESR);
        }
        return new DtoResponse<>(inventories.getTotalElements(),
                mapperUtiliti.mapToCollectionInventoryDto(inventories.getContent()));
    }

    /**
     * выбрать инвентарь по входждению в наименование и идентификатору папки
     * @param request
     * @return 
     */
    public DtoResponse<InventoryDto> findByNameLikeAndFolderId(RequestParametersForDirectories request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);
        Page<InventoryEntity> inventories;
        if ((!request.getQuery().isEmpty() || !request.getQuery().isBlank()) && (request.getFolderId() > 0)) {
            inventories = inventoryRepository.findByNameLikeAndFolderId(pageRequest, request.getQuery(), request.getFolderId());
        } else {
            throw new InvalidRequestParameteException(MESSAGE_BAD_REQUESR);
        }
        return new DtoResponse<>(inventories.getTotalElements(),
                mapperUtiliti.mapToCollectionInventoryDto(inventories.getContent()));
    }

    /**
     * выбрать инвентарь по идентификатору папки
     * @param request
     * @return 
     */
    public DtoResponse<InventoryDto> findByFolderId(RequestParametersForDirectories request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);
        Page<InventoryEntity> inventories;
        if (request.getFolderId() > 0) {
            inventories = inventoryRepository.findByFolderId(pageRequest, request.getFolderId());
        } else {
            throw new InvalidRequestParameteException(MESSAGE_BAD_REQUESR);
        }
        return new DtoResponse<>(inventories.getTotalElements(),
                mapperUtiliti.mapToCollectionInventoryDto(inventories.getContent()));
    }

    /**
     * выбрать весь инвентарь
     * @param request
     * @return 
     */
    public DtoResponse<InventoryDto> findAll(RequestParametersForDirectories request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);
        Page<InventoryEntity> inventories;
        inventories = inventoryRepository.findAll(pageRequest);
        return new DtoResponse<>(inventories.getTotalElements(),
                mapperUtiliti.mapToCollectionInventoryDto(inventories.getContent()));
    }

    // общий запрос
    public DtoResponse<InventoryDto> selectQuery(RequestParametersForDirectories request) {
        if ((!request.getQuery().isEmpty() || !request.getQuery().isBlank()) && (request.getFolderId() == 0)) {
            return findByNameLike(request);
        } else if ((request.getQuery().isEmpty() || request.getQuery().isBlank()) && (request.getFolderId() > 0)) {
            return findByFolderId(request);
        } else if ((!request.getQuery().isEmpty() || !request.getQuery().isBlank()) && (request.getFolderId() > 0)) {
            return findByNameLikeAndFolderId(request);
        }
        return findAll(request);
    }

    // добавить карточку
    public InventoryDto add(InventoryRequest request) {
        InventoryEntity inventoryEntity = fillInventory(new InventoryEntity(), request);
        return mapperUtiliti.mapToInventoryDto(inventoryRepository.save(inventoryEntity));
    }

    // обновить карточку
    public InventoryDto update(long id, InventoryRequest request) {
        InventoryEntity inventoryEntity = fillInventory(mapperUtiliti.mapToInventoryEntity(findById(id)), request);
        return mapperUtiliti.mapToInventoryDto(inventoryRepository.save(inventoryEntity));
    }

    // заполнить карточку инвентаря из запросса
    private InventoryEntity fillInventory(InventoryEntity inventoryEntity, InventoryRequest request) {
        inventoryEntity.setName(request.getName());
        inventoryEntity.setIsDeleted(request.isDeleted());
        inventoryEntity.setFolder(mapperUtiliti.mapToInventoryFolderEntity(inventoryFolderService.findById(request.getFolderId())));
        inventoryEntity.setUnit(mapperUtiliti.mapToUnitEntity(unitService.findById(request.getUnitId())));
        return inventoryEntity;
    }

}
