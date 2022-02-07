package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.request.ParameterRequest;
import ru.home.inventoryaccounting.api.request.InventoryUpdateRequest;
import ru.home.inventoryaccounting.api.response.DTOResponse;
import ru.home.inventoryaccounting.domain.DTO.InventoryDTO;
import ru.home.inventoryaccounting.domain.entity.InventoryEntity;
import ru.home.inventoryaccounting.domain.enums.SortingDirection;
import ru.home.inventoryaccounting.domain.mapper.InventoryFolderMapper;
import ru.home.inventoryaccounting.domain.mapper.InventoryMapper;
import ru.home.inventoryaccounting.domain.mapper.UnitMapper;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.InventoryRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryMapper inventoryMapper;
    private final InventoryFolderMapper inventoryFolderMapper;
    private final UnitMapper unitMapper;
    private final InventoryRepository inventoryRepository;
    private final InventoryFolderService inventoryFolderService;
    private final UnitService unitService;

    /**
     * выбор инвентарь по идентификатору
     */
    public InventoryDTO findById(long id) throws NotFoundException {
        Optional<InventoryEntity> inventory = inventoryRepository.findById(id);
        return inventory.map(inventoryMapper::mapToInventoryDto)
                .orElseThrow(() -> new NotFoundException("Инвентарь с Id: " + id + " не найден."));
    }

    /**
     * выбрать инвентарь по входждению в наименование
     */
    public DTOResponse<InventoryDTO> findByNameLike(ParameterRequest request)
            throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(request);
        Page<InventoryEntity> inventories;
        if (!request.getQuery().isEmpty() || !request.getQuery().isBlank()) {
            inventories = inventoryRepository.findByNameLike(pageRequest, request.getQuery());
        } else {
            throw new InvalidRequestParameteException("Неверный параметр запроса");
        }
        return new DTOResponse<>(inventories.getTotalElements(),
                inventoryMapper.convertCollectionToDTO(inventories.getContent()));
    }

    /**
     * выбрать инвентарь по входждению в наименование и идентификатору папки
     */
    public DTOResponse<InventoryDTO> findByNameLikeAndFolderId(ParameterRequest request)
            throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(request);
        Page<InventoryEntity> inventories;
        if ((!request.getQuery().isEmpty() || !request.getQuery().isBlank()) && (request.getFolderId() > 0)) {
            inventories = inventoryRepository.findByNameLikeAndFolderId(pageRequest, request.getQuery(), request.getFolderId());
        } else {
            throw new InvalidRequestParameteException("Неверный параметр запроса");
        }
        return new DTOResponse<>(inventories.getTotalElements(),
                inventoryMapper.convertCollectionToDTO(inventories.getContent()));
    }

    /**
     * выбрать инвентарь по идентификатору папки
     */
    public DTOResponse<InventoryDTO> findByFolderId(ParameterRequest request) throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(request);
        Page<InventoryEntity> inventories;
        if (request.getFolderId() > 0) {
            inventories = inventoryRepository.findByFolderId(pageRequest, request.getFolderId());
        } else {
            throw new InvalidRequestParameteException("Неверный параметр запроса");
        }
        return new DTOResponse<>(inventories.getTotalElements(),
                inventoryMapper.convertCollectionToDTO(inventories.getContent()));
    }

    /**
     * выбрать весь инвентарь
     */
    public DTOResponse<InventoryDTO> findAll(ParameterRequest request) {
        PageRequest pageRequest = getPageRequest(request);
        Page<InventoryEntity> inventories;
        inventories = inventoryRepository.findAll(pageRequest);
        return new DTOResponse<>(inventories.getTotalElements(),
                inventoryMapper.convertCollectionToDTO(inventories.getContent()));
    }

    //заполнение объекта RequestParameters для передачи в методы
    public ParameterRequest getRequestParameters(int offset, int limit, String query, long folderId, String sortColumns,
                                                 String sortingDirection) {
        ParameterRequest parameterRequest = ParameterRequest.builder()
                .offset(offset)
                .limit(limit)
                .query(query)
                .folderId(folderId)
                .sortColumns(sortColumns.split(", +"))
                .sortingDirection(SortingDirection.valueOf(sortingDirection))
                .build();
        return parameterRequest;
    }

    // общий запрос
    public DTOResponse<InventoryDTO> selectQuery(ParameterRequest request) throws InvalidRequestParameteException {
        //

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
    public InventoryDTO add(InventoryUpdateRequest request) throws NotFoundException {
        InventoryEntity inventoryEntity = fillInventory(new InventoryEntity(),request);
        return inventoryMapper.mapToInventoryDto(inventoryRepository.save(inventoryEntity));
    }

    // обновить карточку
    public InventoryDTO update(long id, InventoryUpdateRequest request) throws NotFoundException {
        InventoryEntity inventoryEntity = fillInventory(inventoryMapper.mapToInventory(findById(id)),request);
        return inventoryMapper.mapToInventoryDto(inventoryRepository.save(inventoryEntity));
    }

    // заполнить карточку инвентаря из запросса
    private InventoryEntity fillInventory(InventoryEntity inventoryEntity, InventoryUpdateRequest request) throws NotFoundException {
        inventoryEntity.setName(request.getName());
        inventoryEntity.setDeleted(request.isDeleted());
        inventoryEntity.setFolder(inventoryFolderMapper.mapToInventoryFolder(inventoryFolderService.findById(request.getFolderId())));
        inventoryEntity.setUnit(unitMapper.mapToUnit(unitService.findById(request.getUnitId())));
        return inventoryEntity;
    }


    // создать страницу пагинации
    private PageRequest getPageRequest(ParameterRequest request) {
        int numberPage = request.getOffset() / request.getLimit();

        if (request.getSortingDirection() == SortingDirection.ASC) {
            return PageRequest.of(numberPage, request.getLimit(), Sort.by(request.getSortColumns()).ascending());
        }
        return PageRequest.of(numberPage, request.getLimit(), Sort.by(request.getSortColumns()).descending());

    }

}
