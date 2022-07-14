package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.request.InventoryFoldeRequest;
import ru.home.inventoryaccounting.api.request.RequestParametersForDirectories;
import ru.home.inventoryaccounting.api.response.DtoResponse;
import ru.home.inventoryaccounting.domain.dto.InventoryFolderDto;
import ru.home.inventoryaccounting.domain.dto.UserDto;
import ru.home.inventoryaccounting.domain.entity.InventoryFolderEntity;
import ru.home.inventoryaccounting.domain.entity.UserEntity;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.InventoryFolderRepository;
import ru.home.inventoryaccounting.util.PageRequestUtil;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryFolderService {

    private final String MESSAGE_NOT_FOUND = "Папка инвентаря с Id: %s не найдена.";
    private final String MESSAGE_BAD_REQUESR = "Неверный параметр запроса";


    private final InventoryFolderRepository inventoryFolderRepository;


    /**
     * выбрать папку по идентификатору
     * @param id
     * @return 
     */
    public InventoryFolderEntity findById(long id) {
        Optional<InventoryFolderEntity> inventoryFolder = inventoryFolderRepository.findById(id);
        return inventoryFolder
                .orElseThrow(() -> new NotFoundException(String.format(MESSAGE_NOT_FOUND, id)));
    }

    /**
     * выбрать папки по входждению в наименование
     */
    public DtoResponse<InventoryFolderDto> findByNameLike(RequestParametersForDirectories request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);
        Page<InventoryFolderEntity> inventoryFolders;
        if (!request.getQuery().isEmpty() || !request.getQuery().isBlank()) {
            inventoryFolders = inventoryFolderRepository.findByNameLike(pageRequest, request.getQuery());
        } else {
            throw new InvalidRequestParameteException(MESSAGE_BAD_REQUESR);
        }
        return new DtoResponse<>(inventoryFolders.getTotalElements(),
                inventoryFolders.getContent().stream().map(InventoryFolderDto::new).collect(Collectors.toList()));
    }

    /**
     * выбрать весе папки
     */
    public DtoResponse<InventoryFolderDto> findAll(RequestParametersForDirectories request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);
        Page<InventoryFolderEntity> inventoryFolders;
        inventoryFolders = inventoryFolderRepository.findAll(pageRequest);
        return new DtoResponse<>(inventoryFolders.getTotalElements(),
                inventoryFolders.getContent().stream().map(InventoryFolderDto::new).collect(Collectors.toList()));
    }


    /**
     * общий запрос
     */
    public DtoResponse<InventoryFolderDto> selectQuery(RequestParametersForDirectories request) {
        if (!request.getQuery().isEmpty() || !request.getQuery().isBlank()) {
            return findByNameLike(request);
        }
        return findAll(request);
    }


    // добавить карточку
    public InventoryFolderDto add(InventoryFoldeRequest request) {
        InventoryFolderEntity inventoryFolderEntity = fillInventory(new InventoryFolderEntity(), request);
        return new InventoryFolderDto(inventoryFolderRepository.save(inventoryFolderEntity));
    }

    // обновить карточку
    public InventoryFolderDto update(long id, InventoryFoldeRequest request) {
        InventoryFolderEntity inventoryFolderEntity = fillInventory(findById(id), request);
        return new InventoryFolderDto(inventoryFolderRepository.save(inventoryFolderEntity));
    }

    // заполнить карточку из запросса
    private InventoryFolderEntity fillInventory(InventoryFolderEntity inventoryFolderEntity, InventoryFoldeRequest request) {
        inventoryFolderEntity.setName(request.getName());
        inventoryFolderEntity.setIsDeleted(request.isDeleted());
        return inventoryFolderEntity;
    }

    /**
     * удалить (переменную is_deleted в true)
     */
    public void deleteById(long id) {
        if (inventoryFolderRepository.updateIsDeleteToTrueById(id) <= 0) {
            throw new NotFoundException(String.format(MESSAGE_NOT_FOUND, id));
        }
    }


    public DtoResponse<InventoryFolderDto> getDeleted(RequestParametersForDirectories request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);
        Page<InventoryFolderEntity> inventoryFolderEntities;
        inventoryFolderEntities = inventoryFolderRepository.getByIsDeletedTrue(pageRequest);
        return new DtoResponse<>(inventoryFolderEntities.getTotalElements(),
                inventoryFolderEntities.getContent().stream().map(InventoryFolderDto::new).collect(Collectors.toList()));
    }
}
