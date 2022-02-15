package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.request.ParameterRequest;
import ru.home.inventoryaccounting.api.request.PartnerRequest;
import ru.home.inventoryaccounting.api.request.UnitRequest;
import ru.home.inventoryaccounting.api.response.DtoResponse;
import ru.home.inventoryaccounting.domain.dto.PartnerDto;
import ru.home.inventoryaccounting.domain.dto.UnitDto;
import ru.home.inventoryaccounting.domain.entity.PartnerEntity;
import ru.home.inventoryaccounting.domain.entity.UnitEntity;
import ru.home.inventoryaccounting.domain.mapper.MapperUtiliti;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.PartnerRepository;
import ru.home.inventoryaccounting.util.PageRequestUtil;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartnerService {

    private final MapperUtiliti mapperUtiliti;
    private final PartnerRepository partnerRepository;

    private final String MESSAGE_NOT_FOUND = "Партнер с Id: %s не найден.";
    private final String MESSAGE_BAD_REQUESR = "Неверный параметр запроса";


    /**
     * выбор партнера по идентификатору
     *
     */
    public PartnerDto findById(long id) throws NotFoundException {
        Optional<PartnerEntity> partner = partnerRepository.findById(id);
        return partner.map(mapperUtiliti::mapToPartnerDto)
                .orElseThrow(() -> new NotFoundException(String.format(MESSAGE_NOT_FOUND, id)));
    }


    /**
     * выбор партнеров по входждению в наименование
     *
     */
    public DtoResponse<PartnerDto> findByNameLike(ParameterRequest request) throws InvalidRequestParameteException {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);
        Page<PartnerEntity> partners;

        if (!request.getQuery().isEmpty() || !request.getQuery().isBlank()) {
            partners = partnerRepository.findByNameLike(pageRequest, request.getQuery());
        } else {
            throw new InvalidRequestParameteException(MESSAGE_BAD_REQUESR);
        }

        return new DtoResponse<>(true, "", partners.getTotalElements(),
                mapperUtiliti.mapToCollectionPartnerDto(partners.getContent()));
    }

    /**
     * выбор всех партнеров
     *
     */
    public DtoResponse<PartnerDto> findAll(ParameterRequest request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);
        Page<PartnerEntity> partners;
        partners = partnerRepository.findAll(pageRequest);
        return new DtoResponse<>(true, "", partners.getTotalElements(),
                mapperUtiliti.mapToCollectionPartnerDto(partners.getContent()));
    }

    /**
     * общий запрос
     */
    public DtoResponse<PartnerDto> selectQuery(ParameterRequest request) throws InvalidRequestParameteException {
        if (!request.getQuery().isEmpty() || !request.getQuery().isBlank()) {
            return findByNameLike(request);
        }
        return findAll(request);
    }

    // добавить карточку
    public PartnerDto add(PartnerRequest request) throws NotFoundException {
        PartnerEntity partnerEntity = fill(new PartnerEntity(), request);
        return mapperUtiliti.mapToPartnerDto(partnerRepository.save(partnerEntity));
    }

    // обновить карточку
    public PartnerDto update(long id, PartnerRequest request) throws NotFoundException {
        PartnerEntity partnerEntity = fill(mapperUtiliti.mapToPartnerEntity(findById(id)), request);
        return mapperUtiliti.mapToPartnerDto(partnerRepository.save(partnerEntity));
    }

    // заполнить карточку из запросса
    private PartnerEntity fill(PartnerEntity partnerEntity, PartnerRequest request) {
        partnerEntity.setName(request.getName());
        partnerEntity.setIsDeleted(request.isDeleted());

        return partnerEntity;
    }

    /**
     * удалить (переменную is_deleted в true)
     */
    public void deleteById(long id) throws NotFoundException {
        if (partnerRepository.updateIsDeleteToTrueById(id) <= 0) {
            throw new NotFoundException(String.format(MESSAGE_NOT_FOUND, id));
        }
    }

}
