package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.request.DocumentHeaderRequest;
import ru.home.inventoryaccounting.api.request.RequestParametersForDocHeader;
import ru.home.inventoryaccounting.api.response.DtoResponse;
import ru.home.inventoryaccounting.domain.dto.DocumentHeaderDto;
import ru.home.inventoryaccounting.domain.entity.DocumentHeaderEntity;
import ru.home.inventoryaccounting.domain.mapper.MapperUtiliti;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.DocumentHeaderRepository;
import ru.home.inventoryaccounting.util.PageRequestUtil;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentsHeaderService {

    private final String MESSAGE_NOT_FOUND = "Документ с Id: %s не найден.";
    private final String MESSAGE_BAD_REQUESR = "Неверный параметр запроса";

    private final DocumentHeaderRepository documentHeaderRepository;
    private final PartnerService partnerService;
    private final UserService userService;
    private final WarehouseService warehouseService;
    private final MapperUtiliti mapperUtiliti;

    /**
     * выбор документа по идентификатору
     * @param id
     * @return 
     */
    public DocumentHeaderDto findById(long id) {
        Optional<DocumentHeaderEntity> documentsHeader = documentHeaderRepository.findById(id);
        return documentsHeader.map(mapperUtiliti::mapToDocumentHeaderDto)
                .orElseThrow(() -> new NotFoundException(MESSAGE_NOT_FOUND));
    }

    /**
     * выбор документов по вхождению в номер документа 
     * @param request
     * @return 
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
                mapperUtiliti.mapToCollectionDocumentHeaderDto(documentHeaders.getContent()));
    }

    /**
     * выбор документов за интервал по типу документа
     * @param request
     * @return 
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
                mapperUtiliti.mapToCollectionDocumentHeaderDto(documentHeaders.getContent()));
    }

    /**
     * выбор документов за интервал по типу документа и по складу
     * @param request
     * @return 
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
                mapperUtiliti.mapToCollectionDocumentHeaderDto(documentHeaders.getContent()));
    }

    /**
     * выбор документов за интервал и по партнеру
     * @param request
     * @return 
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
                mapperUtiliti.mapToCollectionDocumentHeaderDto(documentHeaders.getContent()));
    }

    /**
     * выбор документов за интервал по партнеру и типу документа
     * @param request
     * @return 
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
                mapperUtiliti.mapToCollectionDocumentHeaderDto(documentHeaders.getContent()));
    }

    /**
     * выбор документов за интервал
     * @param request
     * @return 
     */
    public DtoResponse<DocumentHeaderDto> findByDate(RequestParametersForDocHeader request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);//getPageRequest(request.getOffset(), request.getLimit());
        Page<DocumentHeaderEntity> documentHeaders;
        documentHeaders = documentHeaderRepository.findByDate(request.getIntervalStart(), request.getIntervalEnd(), pageRequest);
        return new DtoResponse<>(documentHeaders.getTotalElements(),
                mapperUtiliti.mapToCollectionDocumentHeaderDto(documentHeaders.getContent()));
    }

    /**
     * выбор документов за интервал и по складу
     * @param request
     * @return 
     */
    public DtoResponse<DocumentHeaderDto> findByDateAndWarehouse(RequestParametersForDocHeader request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);//getPageRequest(request.getOffset(), request.getLimit());
        Page<DocumentHeaderEntity> documentHeaders;
        if (request.getWarehouseId() > 0) {
//            documentHeaders = documentHeaderRepository.findByDateAndWarehouse(request.getIntervalStart(), request.getIntervalEnd(),
//                    request.getWarehouseId(), pageRequest);
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
                mapperUtiliti.mapToCollectionDocumentHeaderDto(documentHeaders.getContent()));
    }

    /**
     * выбор документов за интервал по партнеру и по складу
     * @param request
     * @return 
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
                mapperUtiliti.mapToCollectionDocumentHeaderDto(documentHeaders.getContent()));
    }

    /**
     * общий запрос
     * @param request
     * @return 
     */
    public DtoResponse<DocumentHeaderDto> selectQuery(RequestParametersForDocHeader request) {

        if (!request.getQuery().isEmpty() || !request.getQuery().isBlank()) {
            return findByDocumentNumber(request);// выборка по номеру документа
        } else {
            if ((request.getPartnerId() > 0) && (request.getWarehouseId() > 0) && (request.getTypeDok() == 0)) {
                return findByDateAndPartnerAndWarehouse(request); // выборка за период по партнеру и складу
            } else if ((request.getPartnerId() == 0) && (request.getWarehouseId() == 0) && (request.getTypeDok() == 0)) {
                return findByDate(request);// выборка документов за интервал
            } else if ((request.getPartnerId() > 0) && (request.getWarehouseId() == 0) && (request.getTypeDok() == 0)) {
                return findByDateAndPartner(request);// выбор документов за интервал и по партнеру
            } else if ((request.getPartnerId() > 0) && (request.getWarehouseId() == 0) && (request.getTypeDok() > 0)) {
                return findByDateAndPartnerAndTypeDok(request);// выбор документов за интервал по партнеру и типу документа
            }else if ((request.getPartnerId() == 0) && (request.getWarehouseId() > 0) && (request.getTypeDok() > 0)) {
                return findByDateAndTypeDokAndWarehouse(request);// выбор документов за интервал по типу документа и по складу
            } else if ((request.getPartnerId() == 0) && (request.getWarehouseId() == 0) && (request.getTypeDok() > 0)) {
                return findByDateAndTypeDok(request);// выбор документов за интервал по типу документа
            }
        }
        return findByDateAndWarehouse(request); //выбор документов за интервал и по складу
    }

    /**
     * удалить (переменную is_deleted в true)
     * @param id
     */
    public void deleteById(long id) {
        int countDelete = documentHeaderRepository.updateIsDeleteToTrueById(id);
        if (countDelete <= 0) {
            throw new NotFoundException(String.format(MESSAGE_NOT_FOUND, id));
        }
    }

    // добавить документ
    public DocumentHeaderDto add(DocumentHeaderRequest request) {
        DocumentHeaderEntity documentHeaderEntity = fillDocumentHeader(new DocumentHeaderEntity(), request);
        return mapperUtiliti.mapToDocumentHeaderDto(documentHeaderRepository.save(documentHeaderEntity));
    }

    // обновить документ
    public DocumentHeaderDto update(long id, DocumentHeaderRequest request) {
        DocumentHeaderEntity documentHeaderEntity = fillDocumentHeader(mapperUtiliti.mapToDocumentHeaderEntity(findById(id)), request);
        return mapperUtiliti.mapToDocumentHeaderDto(documentHeaderRepository.save(documentHeaderEntity));
    }

    /**
     * заполнить документ из запросса
     * @param documentHeaderEntity
     * @param request
     * @return DocumentHeaderEntity
     */
    private DocumentHeaderEntity fillDocumentHeader(DocumentHeaderEntity documentHeaderEntity, DocumentHeaderRequest request) {
        documentHeaderEntity.setAmount(request.getAmount());
        documentHeaderEntity.setComment(request.getComment());
        documentHeaderEntity.setDate(request.getDate());
        documentHeaderEntity.setIsDeleted(request.getIsDeleted());
        documentHeaderEntity.setDocumentNumber(request.getDocumentNumber());
        documentHeaderEntity.setIsRegistered(request.getIsRegistered());
        documentHeaderEntity.setPartner(mapperUtiliti.mapToPartnerEntity(partnerService.findById(request.getPartnerId())));
        documentHeaderEntity.setUser(mapperUtiliti.mapToUserEntity(userService.findById(request.getUserId())));
        documentHeaderEntity.setWarehouse(mapperUtiliti.mapToWarehouseEntity(warehouseService.findById(request.getWarehouseId())));
        if (request.getWarehouseRecipientId() != null) {
            documentHeaderEntity.setWarehouseRecipient(mapperUtiliti.mapToWarehouseEntity(warehouseService.findById(request.getWarehouseRecipientId())));
        }
        documentHeaderEntity.setTypeDok(request.getTypeDok());

        return documentHeaderEntity;
    }


    // создать страницу пагинации
//    private PageRequest getPageRequest(int offset, int limit) {
//        int numberPage = offset / limit;
//
//        return PageRequest.of(numberPage, limit);
//    }
}
