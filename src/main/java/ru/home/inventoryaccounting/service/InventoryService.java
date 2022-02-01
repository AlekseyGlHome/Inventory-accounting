package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
     * @param offset - номер страницы
     * @param limit  - количество элементов на странице
     * @param query  - строка поиска
     * @return DTOResponse&lt;InventoryDTO&gt;
     * @throws InvalidRequestParameteException
     */
    public DTOResponse<InventoryDTO> findByNameLike(int offset, int limit,
                                                    String query) throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<Inventory> inventories;
        if (!query.isEmpty() || !query.isBlank()) {
            inventories = inventoryRepository.findByNameLike(pageRequest, query);
        } else {
            throw new InvalidRequestParameteException("Неверный параметр запроса");
        }

        return new DTOResponse<>(inventories.getTotalElements(),
                inventoryMapper.convertCollectionToDTO(inventories.getContent()));
    }

    /**
     * выбрать инвентарь по входждению в наименование и идентификатору папки
     *
     * @param offset   - номер страницы
     * @param limit    - количество элементов на странице
     * @param query    - строка поиска
     * @param folderId - идентификатор папки
     * @return DTOResponse&lt;InventoryDTO&gt;
     * @throws InvalidRequestParameteException
     */
    public DTOResponse<InventoryDTO> findByNameLikeAndFolderId(int offset, int limit,
                                                               String query,
                                                               long folderId) throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<Inventory> inventories;
        if ((!query.isEmpty() || !query.isBlank()) && (folderId > 0)) {
            inventories = inventoryRepository.findByNameLikeAndFolderId(pageRequest, query, folderId);
        } else {
            throw new InvalidRequestParameteException("Неверный параметр запроса");
        }

        return new DTOResponse<>(inventories.getTotalElements(),
                inventoryMapper.convertCollectionToDTO(inventories.getContent()));
    }

    /**
     * выбрать инвентарь по идентификатору папки
     *
     * @param offset   - номер страницы
     * @param limit    - количество элементов на странице
     * @param folderId - идентификатор папки
     * @return DTOResponse&lt;InventoryDTO&gt;
     * @throws InvalidRequestParameteException
     */
    public DTOResponse<InventoryDTO> findByFolderId(int offset, int limit,
                                                    long folderId) throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<Inventory> inventories;
        if (folderId > 0) {
            inventories = inventoryRepository.findByFolderId(pageRequest, folderId);
        } else {
            throw new InvalidRequestParameteException("Неверный параметр запроса");
        }

        return new DTOResponse<>(inventories.getTotalElements(),
                inventoryMapper.convertCollectionToDTO(inventories.getContent()));
    }

    /**
     * выбрать весь инвентарь
     *
     * @param offset - номер страницы
     * @param limit  - количество элементов на страницы
     * @return DTOResponse&lt;InventoryDTO&gt;
     */
    public DTOResponse<InventoryDTO> findAll(int offset, int limit) {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<Inventory> inventories;
        inventories = inventoryRepository.findAll(pageRequest);
        return new DTOResponse<>(inventories.getTotalElements(),
                inventoryMapper.convertCollectionToDTO(inventories.getContent()));
    }

    public DTOResponse<InventoryDTO> selectQuery(InventoryRequest inventoryRequest) throws InvalidRequestParameteException {

        //if (query)
        return null;

    }

    // создать страницу пагинации
    private PageRequest getPageRequest(int offset, int limit) {
        int numberPage = offset / limit;

        return PageRequest.of(numberPage, limit);
    }
}
