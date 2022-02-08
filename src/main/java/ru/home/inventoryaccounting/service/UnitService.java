package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.response.DtoResponse;
import ru.home.inventoryaccounting.domain.dto.UnitDto;
import ru.home.inventoryaccounting.domain.entity.UnitEntity;
import ru.home.inventoryaccounting.domain.mapper.MapperUtiliti;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.UnitRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UnitService {

    private final MapperUtiliti mapperUtiliti;
    private final UnitRepository unitRepository;

    /**
     * выбор единицы измерения по идентификатору
     *
     * @param id - идентификатор
     * @return UnitDTO
     */
    public UnitDto findById(long id) throws NotFoundException {
        Optional<UnitEntity> unit = unitRepository.findById(id);
        return unit.map(mapperUtiliti::mapToUnitDto)
                .orElseThrow(() -> new NotFoundException("Единица измерения с Id: " + id + " не найдена."));
    }

    /**
     * выбор единицы измерения по входждению в наименование
     *
     * @param offset - номер страницы
     * @param limit  - количество элементов на странице
     * @param query  - строка поиска
     * @return DTOResponse&lt;UnitDTO&gt;
     */
    public DtoResponse<UnitDto> findByQueryString(int offset, int limit, String query) throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<UnitEntity> units;

        if (!query.isEmpty() || !query.isBlank()) {
            units = unitRepository.findByNameLike(pageRequest, query);
        } else {
            throw new InvalidRequestParameteException("Неверный параметр запроса");
        }

        return new DtoResponse<>(units.getTotalElements(), mapperUtiliti.mapToCollectionUnitDto(units.getContent()));
    }

    /**
     * выбор всех единиц измерения
     *
     * @param offset - номер страницы
     * @param limit  - количество элементов на странице
     * @return DTOResponse&lt;UnitDTO&gt;
     */
    public DtoResponse<UnitDto> findAll(int offset, int limit) {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<UnitEntity> units;
        units = unitRepository.findAll(pageRequest);
        return new DtoResponse<>(units.getTotalElements(), mapperUtiliti.mapToCollectionUnitDto(units.getContent()));
    }

    // создать страницу пагинации
    private PageRequest getPageRequest(int offset, int limit) {
        int numberPage = offset / limit;

        return PageRequest.of(numberPage, limit);
    }

}
