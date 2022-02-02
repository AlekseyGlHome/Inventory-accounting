package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.request.InventoryRequest;
import ru.home.inventoryaccounting.api.response.DTOResponse;
import ru.home.inventoryaccounting.domain.DTO.InventoryDTO;
import ru.home.inventoryaccounting.domain.entity.Inventory;
import ru.home.inventoryaccounting.domain.mapper.InventoryMapper;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.InventoryRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryMapper inventoryMapper;
    private final InventoryRepository inventoryRepository;

    /**
     * выбор инвентарь по идентификатору
     *
     * @param id - идентификатор
     * @return InventoryDTO
     * @throws NotFoundException
     */
    public InventoryDTO findById(long id) throws NotFoundException {
        Optional<Inventory> inventory = inventoryRepository.findById(id);
        return inventory.map(inventoryMapper::convertToDTO)
                .orElseThrow(() -> new NotFoundException("Запись с Id: " + id + " не найдена."));
    }

    /**
     * выбрать инвентарь по входждению в наименование
     *
     * @param request - параметры
     * @return DTOResponse&lt;InventoryDTO&gt;
     * @throws InvalidRequestParameteException
     */
    public DTOResponse<InventoryDTO> findByNameLike(InventoryRequest request)
            throws InvalidRequestParameteException {

        PageRequest pageRequest = getPageRequest(request);
        Page<Inventory> inventories;
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
     *
     * @param request - параметры
     * @return DTOResponse&lt;InventoryDTO&gt;
     * @throws InvalidRequestParameteException
     */
    public DTOResponse<InventoryDTO> findByNameLikeAndFolderId(InventoryRequest request)
            throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(request);
        Page<Inventory> inventories;
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
     *
     * @param request - Параметры
     * @return DTOResponse&lt;InventoryDTO&gt;
     * @throws InvalidRequestParameteException
     */
    public DTOResponse<InventoryDTO> findByFolderId(InventoryRequest request) throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(request);
        Page<Inventory> inventories;
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
     *
     * @param request - параметры
     * @return DTOResponse&lt;InventoryDTO&gt;
     */
    public DTOResponse<InventoryDTO> findAll(InventoryRequest request) {
        PageRequest pageRequest = getPageRequest(request);
        Page<Inventory> inventories;


        inventories = inventoryRepository.findAll(pageRequest);
        return new DTOResponse<>(inventories.getTotalElements(),
                inventoryMapper.convertCollectionToDTO(inventories.getContent()));
    }

    public DTOResponse<InventoryDTO> selectQuery(InventoryRequest request) throws InvalidRequestParameteException {
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

    // создать страницу пагинации
    private PageRequest getPageRequest(InventoryRequest request) {
        int numberPage = request.getOffset() / request.getLimit();

        if (request.isSortAscending()) {
            return PageRequest.of(numberPage, request.getLimit(), Sort.by(request.getSortingName()).ascending());
        }
        return PageRequest.of(numberPage, request.getLimit(), Sort.by(request.getSortingName()).descending());

    }
}
