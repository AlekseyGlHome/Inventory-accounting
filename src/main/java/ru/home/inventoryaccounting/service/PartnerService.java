package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.request.PartnerRequest;
import ru.home.inventoryaccounting.api.request.RequestParametersForDirectories;
import ru.home.inventoryaccounting.api.response.DtoResponse;
import ru.home.inventoryaccounting.domain.dto.PartnerDto;
import ru.home.inventoryaccounting.domain.entity.PartnerEntity;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.PartnerRepository;
import ru.home.inventoryaccounting.util.PageRequestUtil;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartnerService {

    private final PartnerRepository partnerRepository;

    private final String MESSAGE_NOT_FOUND = "Партнер с Id: %s не найден.";
    private final String MESSAGE_BAD_REQUESR = "Неверный параметр запроса";


    /**
     * выбор партнера по идентификатору
     */
    public PartnerEntity findById(long id) {
        Optional<PartnerEntity> partner = partnerRepository.findById(id);
        return partner
                .orElseThrow(() -> new NotFoundException(String.format(MESSAGE_NOT_FOUND, id)));
    }


    /**
     * выбор партнеров по входждению в наименование
     */
    public DtoResponse<PartnerDto> findByNameLike(RequestParametersForDirectories request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);
        Page<PartnerEntity> partners;

        if (!request.getQuery().isEmpty() || !request.getQuery().isBlank()) {
            partners = partnerRepository.findByNameLike(pageRequest, request.getQuery());
        } else {
            throw new InvalidRequestParameteException(MESSAGE_BAD_REQUESR);
        }

        return new DtoResponse<>(partners.getTotalElements(),
                partners.getContent().stream().map(PartnerDto::new).collect(Collectors.toList()));
    }

    /**
     * выбор всех партнеров
     */
    public DtoResponse<PartnerDto> findAll(RequestParametersForDirectories request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);
        Page<PartnerEntity> partners;
        partners = partnerRepository.findAll(pageRequest);
        return new DtoResponse<>(partners.getTotalElements(),
                partners.getContent().stream().map(PartnerDto::new).collect(Collectors.toList()));
    }

    /**
     * общий запрос
     */
    public DtoResponse<PartnerDto> selectQuery(RequestParametersForDirectories request) {
        if (!request.getQuery().isEmpty() || !request.getQuery().isBlank()) {
            return findByNameLike(request);
        }
        return findAll(request);
    }

    // добавить карточку
    public PartnerDto add(PartnerRequest request) {
        PartnerEntity partnerEntity = fill(new PartnerEntity(), request);
        return new PartnerDto(partnerRepository.save(partnerEntity));
    }

    // обновить карточку
    public PartnerDto update(long id, PartnerRequest request) {
        PartnerEntity partnerEntity = fill(findById(id), request);
        return new PartnerDto(partnerRepository.save(partnerEntity));
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
    public void deleteById(long id) {
        if (partnerRepository.updateIsDeleteToTrueById(id) <= 0) {
            throw new NotFoundException(String.format(MESSAGE_NOT_FOUND, id));
        }
    }

    public DtoResponse<PartnerDto> getDeleted(RequestParametersForDirectories request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);
        Page<PartnerEntity> partnerEntities;
        partnerEntities = partnerRepository.getByIsDeletedTrue(pageRequest);
        return new DtoResponse<>(partnerEntities.getTotalElements(),
                partnerEntities.getContent().stream().map(PartnerDto::new).collect(Collectors.toList()));
    }
}
