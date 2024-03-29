package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.home.inventoryaccounting.api.request.DocumentHeaderRequest;
import ru.home.inventoryaccounting.api.request.RequestParametersForDocHeader;
import ru.home.inventoryaccounting.api.response.DtoResponse;
import ru.home.inventoryaccounting.domain.dto.DocumentHeaderAndBodyDto;
import ru.home.inventoryaccounting.domain.dto.DocumentHeaderDto;
import ru.home.inventoryaccounting.domain.entity.DocumentBodyEntity;
import ru.home.inventoryaccounting.domain.entity.DocumentHeaderEntity;
import ru.home.inventoryaccounting.domain.entity.DocumentRegistrationEntity;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.DocumentHeaderRepository;
import ru.home.inventoryaccounting.repository.DocumentRegistryRepository;
import ru.home.inventoryaccounting.util.PageRequestUtil;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final String MESSAGE_NOT_FOUND = "Документ с Id: %s не найден.";
    private final String MESSAGE_BAD_REQUESR = "Неверный параметр запроса";

    private final DocumentHeaderRepository documentHeaderRepository;
    private final PartnerService partnerService;
    private final UserService userService;
    private final WarehouseService warehouseService;
    private final InventoryService inventoryService;
    private final DocumentsBodyService documentsBodyService;
    private final DocumentRegistryRepository documentRegistryRepository;

    /**
     * Выбор заголовка документа по идентификатору
     */
    public DocumentHeaderEntity getById(long id) {
        Optional<DocumentHeaderEntity> documentsHeader = documentHeaderRepository.findById(id);
        return documentsHeader.orElseThrow(() -> new NotFoundException(String.format(MESSAGE_NOT_FOUND, id)));
    }

    /**
     * Выбор документа с телом по идентификатору
     */
    public DocumentHeaderAndBodyDto getFullById(long id) {
        Optional<DocumentHeaderEntity> documentsHeader = documentHeaderRepository.findById(id);
        return documentsHeader.map(DocumentHeaderAndBodyDto::new)
                .orElseThrow(() -> new NotFoundException(String.format(MESSAGE_NOT_FOUND, id)));
    }


    /**
     * Выбор заголовков документов по вхождению в номер документа
     */
    public DtoResponse<DocumentHeaderDto> findByDocumentNumber(RequestParametersForDocHeader request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);// getPageRequest(request.getOffset(), request.getLimit());
        Page<DocumentHeaderEntity> documentHeaders;
        if (!request.getQuery().isEmpty() || !request.getQuery().isBlank()) {
            documentHeaders = documentHeaderRepository.findByDocumentNumber(request.getQuery(), pageRequest);
        } else {
            throw new InvalidRequestParameteException(MESSAGE_BAD_REQUESR);
        }
        return new DtoResponse<>(documentHeaders.getTotalElements(),
                documentHeaders.getContent().stream().map(DocumentHeaderDto::new).collect(Collectors.toList()));
    }

    /**
     * Выбор заголовков документов за интервал по типу документа
     */
    public DtoResponse<DocumentHeaderDto> findByDateAndTypeDok(RequestParametersForDocHeader request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);//getPageRequest(request.getOffset(), request.getLimit());
        Page<DocumentHeaderEntity> documentHeaders;
        if (request.getTypeDok() > 0) {
            documentHeaders = documentHeaderRepository.findByDateAndTypeDok(request.getIntervalStart(), request.getIntervalEnd(),
                    request.getTypeDok(), pageRequest);
        } else {
            throw new InvalidRequestParameteException(MESSAGE_BAD_REQUESR);
        }
        return new DtoResponse<>(documentHeaders.getTotalElements(),
                documentHeaders.getContent().stream().map(DocumentHeaderDto::new).collect(Collectors.toList()));
    }

    /**
     * Выбор заголовков документов за интервал по типу документа и по складу
     */
    public DtoResponse<DocumentHeaderDto> findByDateAndTypeDokAndWarehouse(RequestParametersForDocHeader request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);//getPageRequest(request.getOffset(), request.getLimit());
        Page<DocumentHeaderEntity> documentHeaders;
        if (request.getTypeDok() > 0 && request.getWarehouseId() > 0) {
            documentHeaders = documentHeaderRepository
                    .findByDateAndTypeDokAndWarehouse(request.getIntervalStart(), request.getIntervalEnd(), request.getTypeDok(),
                            request.getWarehouseId(), pageRequest);
        } else {
            throw new InvalidRequestParameteException(MESSAGE_BAD_REQUESR);
        }
        return new DtoResponse<>(documentHeaders.getTotalElements(),
                documentHeaders.getContent().stream().map(DocumentHeaderDto::new).collect(Collectors.toList()));
    }

    /**
     * Выбор заголовков документов за интервал и по партнеру
     */
    public DtoResponse<DocumentHeaderDto> findByDateAndPartner(RequestParametersForDocHeader request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);//getPageRequest(request.getOffset(), request.getLimit());
        Page<DocumentHeaderEntity> documentHeaders;
        if (request.getPartnerId() > 0) {
            documentHeaders = documentHeaderRepository.findByDateAndPartner(request.getIntervalStart(), request.getIntervalEnd(),
                    request.getPartnerId(), pageRequest);
        } else {
            throw new InvalidRequestParameteException(MESSAGE_BAD_REQUESR);
        }
        return new DtoResponse<>(documentHeaders.getTotalElements(),
                documentHeaders.getContent().stream().map(DocumentHeaderDto::new).collect(Collectors.toList()));
    }

    /**
     * Выбор заголовков документов за интервал по партнеру и типу документа
     */
    public DtoResponse<DocumentHeaderDto> findByDateAndPartnerAndTypeDok(RequestParametersForDocHeader request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);//getPageRequest(request.getOffset(), request.getLimit());
        Page<DocumentHeaderEntity> documentHeaders;
        if (request.getPartnerId() > 0) {
            documentHeaders = documentHeaderRepository.findByDateAndPartnerAndTypeDok(
                    request.getIntervalStart(),
                    request.getIntervalEnd(),
                    request.getPartnerId(),
                    request.getTypeDok(),
                    pageRequest);
        } else {
            throw new InvalidRequestParameteException(MESSAGE_BAD_REQUESR);
        }
        return new DtoResponse<>(documentHeaders.getTotalElements(),
                documentHeaders.getContent().stream().map(DocumentHeaderDto::new).collect(Collectors.toList()));
    }

    /**
     * Выбор заголовков документов за интервал
     */
    public DtoResponse<DocumentHeaderDto> findByDate(RequestParametersForDocHeader request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);//getPageRequest(request.getOffset(), request.getLimit());
        Page<DocumentHeaderEntity> documentHeaders;
        documentHeaders = documentHeaderRepository.findByDate(request.getIntervalStart(), request.getIntervalEnd(), pageRequest);
        return new DtoResponse<>(documentHeaders.getTotalElements(),
                documentHeaders.getContent().stream().map(DocumentHeaderDto::new).collect(Collectors.toList()));
    }

    /**
     * Выбор заголовков документов за интервал и по складу
     */
    public DtoResponse<DocumentHeaderDto> findByDateAndWarehouse(RequestParametersForDocHeader request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);//getPageRequest(request.getOffset(), request.getLimit());
        Page<DocumentHeaderEntity> documentHeaders;
        if (request.getWarehouseId() > 0) {
            documentHeaders = documentHeaderRepository
                    .findByDateBetweenAndWarehouseOrWarehouseRecipient(
                            request.getIntervalStart(),
                            request.getIntervalEnd(),
                            request.getWarehouseId(),
                            pageRequest);
        } else {
            throw new InvalidRequestParameteException(MESSAGE_BAD_REQUESR);
        }
        return new DtoResponse<>(documentHeaders.getTotalElements(),
                documentHeaders.getContent().stream().map(DocumentHeaderDto::new).collect(Collectors.toList()));
    }

    /**
     * Выбор заголовков документов за интервал по партнеру и по складу
     */
    public DtoResponse<DocumentHeaderDto> findByDateAndPartnerAndWarehouse(RequestParametersForDocHeader request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);//getPageRequest(request.getOffset(), request.getLimit());
        Page<DocumentHeaderEntity> documentHeaders;
        if (request.getWarehouseId() > 0 && request.getPartnerId() > 0) {
            documentHeaders = documentHeaderRepository
                    .findByDateAndPartnerAndWarehouse(request.getIntervalStart(), request.getIntervalEnd(),
                            request.getPartnerId(), request.getWarehouseId(), pageRequest);
        } else {
            throw new InvalidRequestParameteException(MESSAGE_BAD_REQUESR);
        }
        return new DtoResponse<>(documentHeaders.getTotalElements(),
                documentHeaders.getContent().stream().map(DocumentHeaderDto::new).collect(Collectors.toList()));
    }

    /**
     * Общий запрос
     */
    public DtoResponse<DocumentHeaderDto> selectQuery(RequestParametersForDocHeader request) {

        if (!request.getQuery().isEmpty() || !request.getQuery().isBlank()) {
            return findByDocumentNumber(request);// выборка по номеру документа
        } else {
            if ((request.getPartnerId() > 0) && (request.getWarehouseId() > 0) && (request.getTypeDok() == 0)) {
                return findByDateAndPartnerAndWarehouse(request); // выборка за период по партнеру и складу
            } else if ((request.getPartnerId() == 0) && (request.getWarehouseId() == 0) && (request.getTypeDok() == 0)) {
                return findByDate(request);// выборка за интервал
            } else if ((request.getPartnerId() > 0) && (request.getWarehouseId() == 0) && (request.getTypeDok() == 0)) {
                return findByDateAndPartner(request);// выборка за интервал и по партнеру
            } else if ((request.getPartnerId() > 0) && (request.getWarehouseId() == 0) && (request.getTypeDok() > 0)) {
                return findByDateAndPartnerAndTypeDok(request);// выборка за интервал по партнеру и типу документа
            } else if ((request.getPartnerId() == 0) && (request.getWarehouseId() > 0) && (request.getTypeDok() > 0)) {
                return findByDateAndTypeDokAndWarehouse(request);// выборка за интервал по типу документа и по складу
            } else if ((request.getPartnerId() == 0) && (request.getWarehouseId() == 0) && (request.getTypeDok() > 0)) {
                return findByDateAndTypeDok(request);// выборка за интервал по типу документа
            }
        }
        return findByDateAndWarehouse(request); //выборка за интервал и по складу
    }

    /**
     * Удалить (переменную is_deleted в true)
     */
    @Transactional
    public void deleteById(long id) {
        int countDelete = documentHeaderRepository.updateIsDeleteToTrueById(id);
        if (countDelete <= 0) {
            throw new NotFoundException(String.format(MESSAGE_NOT_FOUND, id));
        }
    }
    /**
     * Добавить документ
     */
    @Transactional
    public DocumentHeaderAndBodyDto add(DocumentHeaderRequest request) {
        DocumentHeaderEntity documentHeaderEntity = fillDocumentHeader(new DocumentHeaderEntity(), request);
        documentHeaderRepository.save(documentHeaderEntity);
        documentsBodyService.save(documentHeaderEntity.getBodyEntitys());
        addToRegistration(documentHeaderEntity.getId());
        return new DocumentHeaderAndBodyDto(documentHeaderEntity);
    }
    /**
     * Обновить документ
     */
    @Transactional
    public DocumentHeaderAndBodyDto update(long id, DocumentHeaderRequest request) {
        DocumentHeaderEntity oldHeaderEntity = getById(id);
        documentsBodyService.delete(oldHeaderEntity.getBodyEntitys());
        fillDocumentHeader(oldHeaderEntity, request);
        documentsBodyService.save(oldHeaderEntity.getBodyEntitys());
        documentHeaderRepository.save(oldHeaderEntity);
        addToRegistration(oldHeaderEntity.getId());
        return new DocumentHeaderAndBodyDto(oldHeaderEntity);
    }

    /**
     * Добавить документ на регистрацию
     */
    @Transactional
    public void addToRegistration(Long doc_id) {
        DocumentHeaderEntity documentHeaderEntity = getById(doc_id);
        long countRegDoc = documentRegistryRepository.countByDocumentHeaderEntity(documentHeaderEntity);
        if (countRegDoc <= 0) {
            DocumentRegistrationEntity documentRegistrationEntity = new DocumentRegistrationEntity(documentHeaderEntity);
            documentRegistryRepository.save(documentRegistrationEntity);
        }
    }

    /**
     * Получить список документов на регистрации
     */
    public DtoResponse<DocumentHeaderDto> getDocumentRegistration(RequestParametersForDocHeader request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);
        Page<DocumentRegistrationEntity> documentHeaders;
        documentHeaders = documentRegistryRepository.findAll(pageRequest);
        return new DtoResponse<>(documentHeaders.getTotalElements(),
                documentHeaders.getContent().stream().map((o)->new DocumentHeaderDto(o.getDocumentHeaderEntity())).collect(Collectors.toList()));
    }

    /**
     * заполнить документ из запросса
     */
    private DocumentHeaderEntity fillDocumentHeader(DocumentHeaderEntity documentHeaderEntity, DocumentHeaderRequest request) {
        documentHeaderEntity.setAmount(request.getAmount());
        documentHeaderEntity.setComment(request.getComment());
        documentHeaderEntity.setDate(request.getDate());
        documentHeaderEntity.setIsDeleted(request.getIsDeleted());
        documentHeaderEntity.setDocumentNumber(request.getDocumentNumber());
        documentHeaderEntity.setIsRegistered(request.getIsRegistered());
        if (request.getPartnerId() != null) {
            documentHeaderEntity.setPartner(partnerService.findById(request.getPartnerId()));
        }
        documentHeaderEntity.setUser(userService.findById(request.getUserId()));
        documentHeaderEntity.setWarehouse(warehouseService.findById(request.getWarehouseId()));
        if (request.getWarehouseRecipientId() != null) {
            documentHeaderEntity.setWarehouseRecipient(warehouseService.findById(request.getWarehouseRecipientId()));
        }
        documentHeaderEntity.setTypeDok(request.getTypeDok());
        documentHeaderEntity.setBodyEntitys(fillDocumentBody(request, documentHeaderEntity));
        return documentHeaderEntity;
    }

    /**
     * заполнить тело документа из запросса
     */
    private Collection<DocumentBodyEntity> fillDocumentBody(DocumentHeaderRequest request, DocumentHeaderEntity headerEntity) {

        return request.getBodyDto()
                .stream()
                .map((d) -> new DocumentBodyEntity(d, inventoryService.findById(d.getInventoryId()), headerEntity))
                .collect(Collectors.toList());
    }

    /**
     * Получить список удаленнных документов
     */
    public DtoResponse<DocumentHeaderDto> getDeleted(RequestParametersForDocHeader request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);
        Page<DocumentHeaderEntity> documentHeaderEntities;
        documentHeaderEntities = documentHeaderRepository.getByIsDeletedTrue(pageRequest);
        return new DtoResponse<>(documentHeaderEntities.getTotalElements(),
                documentHeaderEntities.getContent().stream().map(DocumentHeaderDto::new).collect(Collectors.toList()));
    }
}
