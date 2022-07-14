package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.request.RequestParametersForDirectories;
import ru.home.inventoryaccounting.api.request.UnitRequest;
import ru.home.inventoryaccounting.api.response.DtoResponse;
import ru.home.inventoryaccounting.domain.dto.UnitDto;
import ru.home.inventoryaccounting.domain.dto.UserDto;
import ru.home.inventoryaccounting.domain.entity.UnitEntity;
import ru.home.inventoryaccounting.domain.entity.UserEntity;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.UnitRepository;
import ru.home.inventoryaccounting.util.PageRequestUtil;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UnitService {


    private final UnitRepository unitRepository;

    private final String MESSAGE_NOT_FOUND = "Единица измерения с Id: %s не найдена.";
    private final String MESSAGE_BAD_REQUESR = "Неверный параметр запроса";

    /**
     * выбор единицы измерения по идентификатору
     */
    public UnitEntity findById(long id) {
        try {
            Optional<UnitEntity> unit = unitRepository.findById(id);
            return unit.orElseThrow(() -> new NotFoundException(String.format(MESSAGE_NOT_FOUND, id)));
        }catch (Exception ex){
            throw new InvalidRequestParameteException(ex.getMessage());
        }

    }


    /**
     * выбор единицы измерения по входждению в наименование
     */
    public DtoResponse<UnitDto> findByNameLike(RequestParametersForDirectories request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);
        Page<UnitEntity> units;

        if (!request.getQuery().isEmpty() || !request.getQuery().isBlank()) {
            units = unitRepository.findByNameLike(pageRequest, request.getQuery());
        } else {
            throw new InvalidRequestParameteException(MESSAGE_BAD_REQUESR);
        }

        return new DtoResponse<>(units.getTotalElements(),
                units.getContent().stream().map(UnitDto::new).collect(Collectors.toList()));
    }

    /**
     * выбор всех единиц измерения
     */
    public DtoResponse<UnitDto> findAll(RequestParametersForDirectories request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);
        Page<UnitEntity> units;
        units = unitRepository.findAll(pageRequest);
        return new DtoResponse<>(units.getTotalElements(),
                units.getContent().stream().map(UnitDto::new).collect(Collectors.toList()));
    }

    /**
     * общий запрос
     */
    public DtoResponse<UnitDto> selectQuery(RequestParametersForDirectories request) {
        if (!request.getQuery().isEmpty() || !request.getQuery().isBlank()) {
            return findByNameLike(request);
        }
        return findAll(request);
    }

    // добавить карточку
    public UnitDto add(UnitRequest request) {
        UnitEntity unitEntity = fill(new UnitEntity(), request);
        return new UnitDto(unitRepository.save(unitEntity));
    }

    // обновить карточку
    public UnitDto update(long id, UnitRequest request) {
        UnitEntity unitEntity = fill(findById(id), request);
        return new UnitDto(unitRepository.save(unitEntity));
    }

    // заполнить карточку из запросса
    private UnitEntity fill(UnitEntity unitEntity, UnitRequest request) {
        unitEntity.setName(request.getName());
        unitEntity.setIsDeleted(request.isDeleted());

        return unitEntity;
    }

    /**
     * удалить (переменную is_deleted в true)
     */
    public void deleteById(long id) {
        if (unitRepository.updateIsDeleteToTrueById(id) <= 0) {
            throw new NotFoundException(String.format(MESSAGE_NOT_FOUND, id));
        }
    }

    public DtoResponse<UnitDto> getDeleted(RequestParametersForDirectories request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);
        Page<UnitEntity> unitEntities;
        unitEntities = unitRepository.getByIsDeletedTrue(pageRequest);
        return new DtoResponse<>(unitEntities.getTotalElements(),
                unitEntities.getContent().stream().map(UnitDto::new).collect(Collectors.toList()));
    }

}
