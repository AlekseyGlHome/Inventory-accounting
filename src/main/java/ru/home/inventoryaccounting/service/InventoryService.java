package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.request.ParameterRequest;
import ru.home.inventoryaccounting.api.request.InventoryUpdateRequest;
import ru.home.inventoryaccounting.api.response.DtoResponse;
import ru.home.inventoryaccounting.domain.dto.InventoryDto;
import ru.home.inventoryaccounting.domain.entity.InventoryEntity;
import ru.home.inventoryaccounting.domain.enums.SortingDirection;
import ru.home.inventoryaccounting.domain.mapper.MapperUtiliti;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.InventoryRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final MapperUtiliti mapperUtiliti;
    private final InventoryRepository inventoryRepository;
    private final InventoryFolderService inventoryFolderService;
    private final UnitService unitService;

    /**
     * выбор инвентарь по идентификатору
     */
    public InventoryDto findById(long id) throws NotFoundException {
        Optional<InventoryEntity> inventory = inventoryRepository.findById(id);
        return inventory.map(mapperUtiliti::mapToInventoryDto)
                .orElseThrow(() -> new NotFoundException("Инвентарь с Id: " + id + " не найден."));
    }

    public void deleteById(long id) throws NotFoundException {
        try {
            inventoryRepository.deleteById(id);
        }catch (EmptyResultDataAccessException ex){
            throw new NotFoundException("Инвентарь с Id: " + id + " не найден.");
        }

    }

    /**
     * выбрать инвентарь по входждению в наименование
     */
    public DtoResponse<InventoryDto> findByNameLike(ParameterRequest request)
            throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(request);
        Page<InventoryEntity> inventories;
        if (!request.getQuery().isEmpty() || !request.getQuery().isBlank()) {
            inventories = inventoryRepository.findByNameLike(pageRequest, request.getQuery());
        } else {
            throw new InvalidRequestParameteException("Неверный параметр запроса");
        }
        return new DtoResponse<>(inventories.getTotalElements(),
                mapperUtiliti.mapToCollectionInventoryDto(inventories.getContent()));
    }

    /**
     * выбрать инвентарь по входждению в наименование и идентификатору папки
     */
    public DtoResponse<InventoryDto> findByNameLikeAndFolderId(ParameterRequest request)
            throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(request);
        Page<InventoryEntity> inventories;
        if ((!request.getQuery().isEmpty() || !request.getQuery().isBlank()) && (request.getFolderId() > 0)) {
            inventories = inventoryRepository.findByNameLikeAndFolderId(pageRequest, request.getQuery(), request.getFolderId());
        } else {
            throw new InvalidRequestParameteException("Неверный параметр запроса");
        }
        return new DtoResponse<>(inventories.getTotalElements(),
                mapperUtiliti.mapToCollectionInventoryDto(inventories.getContent()));
    }

    /**
     * выбрать инвентарь по идентификатору папки
     */
    public DtoResponse<InventoryDto> findByFolderId(ParameterRequest request) throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(request);
        Page<InventoryEntity> inventories;
        if (request.getFolderId() > 0) {
            inventories = inventoryRepository.findByFolderId(pageRequest, request.getFolderId());
        } else {
            throw new InvalidRequestParameteException("Неверный параметр запроса");
        }
        return new DtoResponse<>(inventories.getTotalElements(),
                mapperUtiliti.mapToCollectionInventoryDto(inventories.getContent()));
    }

    /**
     * выбрать весь инвентарь
     */
    public DtoResponse<InventoryDto> findAll(ParameterRequest request) {
        PageRequest pageRequest = getPageRequest(request);
        Page<InventoryEntity> inventories;
        inventories = inventoryRepository.findAll(pageRequest);
        return new DtoResponse<>(inventories.getTotalElements(),
                mapperUtiliti.mapToCollectionInventoryDto(inventories.getContent()));
    }

    //заполнение объекта RequestParameters для передачи в методы
    public ParameterRequest getRequestParameters(int offset, int limit, String query, long folderId, String sortColumns,
                                                 String sortingDirection) {
        return ParameterRequest.builder()
                .offset(offset)
                .limit(limit)
                .query(query)
                .folderId(folderId)
                .sortColumns(sortColumns.split(", +"))
                .sortingDirection(SortingDirection.valueOf(sortingDirection))
                .build();
    }

    // общий запрос
    public DtoResponse<InventoryDto> selectQuery(ParameterRequest request) throws InvalidRequestParameteException {
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
    public InventoryDto add(InventoryUpdateRequest request) throws NotFoundException {
        InventoryEntity inventoryEntity = fillInventory(new InventoryEntity(),request);
        return mapperUtiliti.mapToInventoryDto(inventoryRepository.save(inventoryEntity));
    }

    // обновить карточку
    public InventoryDto update(long id, InventoryUpdateRequest request) throws NotFoundException {
        InventoryEntity inventoryEntity = fillInventory(mapperUtiliti.mapToInventoryEntity(findById(id)),request);
        return mapperUtiliti.mapToInventoryDto(inventoryRepository.save(inventoryEntity));
    }

    // заполнить карточку инвентаря из запросса
    private InventoryEntity fillInventory(InventoryEntity inventoryEntity, InventoryUpdateRequest request) throws NotFoundException {
        inventoryEntity.setName(request.getName());
        inventoryEntity.setDeleted(request.isDeleted());
        inventoryEntity.setFolder(mapperUtiliti.mapToInventoryFolderEntity(inventoryFolderService.findById(request.getFolderId())));
        inventoryEntity.setUnit(mapperUtiliti.mapToUnitEntity(unitService.findById(request.getUnitId())));
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
