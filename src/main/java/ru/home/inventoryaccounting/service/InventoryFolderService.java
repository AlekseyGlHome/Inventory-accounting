package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.response.DtoResponse;
import ru.home.inventoryaccounting.domain.dto.InventoryFolderDto;
import ru.home.inventoryaccounting.domain.entity.InventoryFolderEntity;
import ru.home.inventoryaccounting.domain.mapper.MapperUtiliti;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.InventoryFolderRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryFolderService {


    private final MapperUtiliti mapperUtiliti;
    private final InventoryFolderRepository inventoryFolderRepository;

    /**
     * выбрать папку по идентификатору
     *
     * @param id - идентификатор
     * @return InventoryFolderDTO
     */
    public InventoryFolderDto findById(long id) throws NotFoundException {
        Optional<InventoryFolderEntity> inventoryFolder = inventoryFolderRepository.findById(id);
        return inventoryFolder.map(mapperUtiliti::mapToInventoryFolderDto)
                .orElseThrow(() -> new NotFoundException("Папка инвентаря с Id: " + id + " не найдена."));
    }

    /**
     * выбрать папки по входждению в наименование
     *
     * @param offset   - номер страницы
     * @param limit    - количество элементов на странице
     * @param query    - строка поиска
     * @return DTOResponse&lt;InventoryFolderDTO&gt;
     */
    public DtoResponse<InventoryFolderDto> findByQueryString(int offset, int limit, String query) throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<InventoryFolderEntity> inventoryFolders;
        if (!query.isEmpty() || !query.isBlank()) {
            inventoryFolders = inventoryFolderRepository.findByNameLike(pageRequest, query);
        } else {
            throw new InvalidRequestParameteException("Неверный параметр запроса");
        }
        return new DtoResponse<>(inventoryFolders.getTotalElements(),
                mapperUtiliti.mapToCollectionInventoryFolderDto(inventoryFolders.getContent()));
    }

    /**
     * выбрать весе папки
     *
     * @param offset - номер страницы
     * @param limit  - количество элементов на страницы
     * @return DTOResponse&lt;InventoryFolderDTO&gt;
     */
    public DtoResponse<InventoryFolderDto> findAll(int offset, int limit) {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<InventoryFolderEntity> inventoryFolders;
        inventoryFolders = inventoryFolderRepository.findAll(pageRequest);
        return new DtoResponse<>(inventoryFolders.getTotalElements(),
                mapperUtiliti.mapToCollectionInventoryFolderDto(inventoryFolders.getContent()));
    }

    // создать страницу пагинации
    private PageRequest getPageRequest(int offset, int limit) {
        int numberPage = offset / limit;

        return PageRequest.of(numberPage, limit);
    }
}
