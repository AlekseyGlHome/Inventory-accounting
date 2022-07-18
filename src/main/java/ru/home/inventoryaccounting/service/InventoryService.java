package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.home.inventoryaccounting.api.request.InventoryRequest;
import ru.home.inventoryaccounting.api.request.RequestParametersForDirectories;
import ru.home.inventoryaccounting.api.response.DtoResponse;
import ru.home.inventoryaccounting.domain.dto.InventoryDto;
import ru.home.inventoryaccounting.domain.entity.InventoryEntity;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.InventoryRepository;
import ru.home.inventoryaccounting.util.PageRequestUtil;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {


    private final InventoryRepository inventoryRepository;
    private final InventoryFolderService inventoryFolderService;
    private final UnitService unitService;

    private final String MESSAGE_NOT_FOUND = "Инвентарь с Id: %s не найден.";
    private final String MESSAGE_BAD_REQUESR = "Неверный параметр запроса";

    /**
     * выбрать инвентарь по идентификатору
     */
    public InventoryEntity findById(long id) {
        Optional<InventoryEntity> inventory = inventoryRepository.findById(id);
        return inventory
                .orElseThrow(() -> new NotFoundException(String.format(MESSAGE_NOT_FOUND, id)));
    }

    /**
     * удалить (переменную is_deleted в true)
     */
    @Transactional
    public void deleteById(long id) {
        int countDelete = inventoryRepository.updateIsDeleteToTrueById(id);
        if (countDelete <= 0) {
            throw new NotFoundException(String.format(MESSAGE_NOT_FOUND, id));
        }
    }

    /**
     * выбрать инвентарь по входждению в наименование
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
                inventories.getContent().stream().map(InventoryDto::new).collect(Collectors.toList()));
    }

    /**
     * выбрать инвентарь по входждению в наименование и идентификатору папки
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
                inventories.getContent().stream().map(InventoryDto::new).collect(Collectors.toList()));
    }

    /**
     * выбрать инвентарь по идентификатору папки
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
                inventories.getContent().stream().map(InventoryDto::new).collect(Collectors.toList()));
    }

    /**
     * выбрать весь инвентарь
     */
    public DtoResponse<InventoryDto> findAll(RequestParametersForDirectories request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);
        Page<InventoryEntity> inventories;
        inventories = inventoryRepository.findAll(pageRequest);
        return new DtoResponse<>(inventories.getTotalElements(),
                inventories.getContent().stream().map(InventoryDto::new).collect(Collectors.toList()));
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
    @Transactional
    public InventoryDto add(InventoryRequest request) {
        InventoryEntity inventoryEntity = fillInventory(new InventoryEntity(), request);
        return new InventoryDto(inventoryRepository.save(inventoryEntity));
    }

    // обновить карточку
    @Transactional
    public InventoryDto update(long id, InventoryRequest request) {
        InventoryEntity inventoryEntity = fillInventory(findById(id), request);
        return new InventoryDto(inventoryRepository.save(inventoryEntity));
    }

    // заполнить карточку инвентаря из запросса
    private InventoryEntity fillInventory(InventoryEntity inventoryEntity, InventoryRequest request) {
        inventoryEntity.setName(request.getName());
        inventoryEntity.setIsDeleted(request.isDeleted());
        inventoryEntity.setFolder(inventoryFolderService.findById(request.getFolderId()));
        inventoryEntity.setUnit(unitService.findById(request.getUnitId()));
        return inventoryEntity;
    }

    public DtoResponse<InventoryDto> getDeleted(RequestParametersForDirectories request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);
        Page<InventoryEntity> inventoryEntities;
        inventoryEntities = inventoryRepository.getByIsDeletedTrue(pageRequest);
        return new DtoResponse<>(inventoryEntities.getTotalElements(),
                inventoryEntities.getContent().stream().map(InventoryDto::new).collect(Collectors.toList()));
    }
}
