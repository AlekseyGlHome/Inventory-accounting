package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.response.DTOResponse;
import ru.home.inventoryaccounting.domain.DTO.UnitDTO;
import ru.home.inventoryaccounting.domain.entity.UnitEntity;
import ru.home.inventoryaccounting.domain.mapper.UnitMapper;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.UnitRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UnitService {

    private final UnitMapper unitMapper;
    private final UnitRepository unitRepository;

    /**
     * выбор единицы измерения по идентификатору
     *
     * @param id - идентификатор
     * @return UnitDTO
     * @throws NotFoundException
     */
    public UnitDTO findById(long id) throws NotFoundException {
        Optional<UnitEntity> unit = unitRepository.findById(id);
        return unit.map(unitMapper::mapToUnitDto)
                .orElseThrow(() -> new NotFoundException("Единица измерения с Id: " + id + " не найдена."));
    }

    /**
     * выбор единицы измерения по входждению в наименование
     *
     * @param offset - номер страницы
     * @param limit  - количество элементов на странице
     * @param query  - строка поиска
     * @return DTOResponse&lt;UnitDTO&gt;
     * @throws InvalidRequestParameteException
     */
    public DTOResponse<UnitDTO> findByQueryString(int offset, int limit, String query) throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<UnitEntity> units;

        if (!query.isEmpty() || !query.isBlank()) {
            units = unitRepository.findByNameLike(pageRequest, query);
        } else {
            throw new InvalidRequestParameteException("Неверный параметр запроса");
        }

        return new DTOResponse<>(units.getTotalElements(), unitMapper.convertCollectionToDTO(units.getContent()));
    }

    /**
     * выбор всех единиц измерения
     *
     * @param offset - номер страницы
     * @param limit  - количество элементов на странице
     * @return DTOResponse&lt;UnitDTO&gt;
     */
    public DTOResponse<UnitDTO> findAll(int offset, int limit) {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<UnitEntity> units;
        units = unitRepository.findAll(pageRequest);
        return new DTOResponse<>(units.getTotalElements(), unitMapper.convertCollectionToDTO(units.getContent()));
    }

    // создать страницу пагинации
    private PageRequest getPageRequest(int offset, int limit) {
        int numberPage = offset / limit;

        return PageRequest.of(numberPage, limit);
    }

}
