package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.response.DTOResponse;
import ru.home.inventoryaccounting.domain.DTO.InventoryFolderDTO;
import ru.home.inventoryaccounting.domain.entity.InventoryFolder;
import ru.home.inventoryaccounting.domain.mapper.InventoryFolderMapper;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.InventoryFolderRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryFolderService {


    private final InventoryFolderMapper inventoryFolderMapper;
    private final InventoryFolderRepository inventoryFolderRepository;

    /**
     * выбрать папку по идентификатору
     *
     * @param id - идентификатор
     * @return InventoryFolderDTO
     * @throws NotFoundException
     */
    public InventoryFolderDTO findById(long id) throws NotFoundException {
        Optional<InventoryFolder> inventoryFolder = inventoryFolderRepository.findById(id);
        return inventoryFolder.map(inventoryFolderMapper::convertToDTO)
                .orElseThrow(() -> new NotFoundException("Запись с Id: " + id + " не найдена."));
    }

    /**
     * выбрать папки по входждению в наименование
     *
     * @param offset   - номер страницы
     * @param limit    - количество элементов на странице
     * @param query    - строка поиска
     * @return DTOResponse&lt;InventoryFolderDTO&gt;
     * @throws InvalidRequestParameteException
     */
    public DTOResponse<InventoryFolderDTO> findByQueryString(int offset, int limit, String query) throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<InventoryFolder> inventoryFolders;
        if (!query.isEmpty() || !query.isBlank()) {
            inventoryFolders = inventoryFolderRepository.findByNameLike(pageRequest, query);
        } else {
            throw new InvalidRequestParameteException("Неверный параметр запроса");
        }
        return new DTOResponse<>(inventoryFolders.getTotalElements(),
                inventoryFolderMapper.convertCollectionToDTO(inventoryFolders.getContent()));
    }

    /**
     * выбрать весе папки
     *
     * @param offset - номер страницы
     * @param limit  - количество элементов на страницы
     * @return DTOResponse&lt;InventoryFolderDTO&gt;
     */
    public DTOResponse<InventoryFolderDTO> findAll(int offset, int limit) {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<InventoryFolder> inventoryFolders;
        inventoryFolders = inventoryFolderRepository.findAll(pageRequest);
        return new DTOResponse<>(inventoryFolders.getTotalElements(),
                inventoryFolderMapper.convertCollectionToDTO(inventoryFolders.getContent()));
    }

    // создать страницу пагинации
    private PageRequest getPageRequest(int offset, int limit) {
        int numberPage = offset / limit;

        return PageRequest.of(numberPage, limit);
    }
}
