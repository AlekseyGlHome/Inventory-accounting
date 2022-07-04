package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.request.DocumentHeaderRequest;
import ru.home.inventoryaccounting.api.request.RequestParametersForDocBody;
import ru.home.inventoryaccounting.api.request.RequestParametersForDocHeader;
import ru.home.inventoryaccounting.api.response.DtoResponse;
import ru.home.inventoryaccounting.domain.dto.DocumentBodyDto;
import ru.home.inventoryaccounting.domain.dto.DocumentHeaderDto;
import ru.home.inventoryaccounting.domain.entity.DocumentBodyEntity;
import ru.home.inventoryaccounting.domain.entity.DocumentHeaderEntity;
import ru.home.inventoryaccounting.domain.mapper.MapperUtiliti;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.DocumentBodyRepository;
import ru.home.inventoryaccounting.repository.DocumentHeaderRepository;
import ru.home.inventoryaccounting.util.PageRequestUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentsBodyService {
    private final String MESSAGE_NOT_FOUND = "Данные по документу с Id: %s не найдены.";
    private final String MESSAGE_BAD_REQUESR = "Неверный параметр запроса";

    private final DocumentBodyRepository documentBodyRepository;

    private final MapperUtiliti mapperUtiliti;

    /**
     * выбор документа по идентификатору
     */
    //public DocumentBodyDto findById(long id) {
    //    Optional<DocumentBodyEntity> documentBody = documentBodyRepository.findById(id);
    //    return documentBody.map(mapperUtiliti::mapToDocumentHeaderDto)
    //            .orElseThrow(() -> new NotFoundException(MESSAGE_NOT_FOUND));
    //}

    /**
     * выбор документов по вхождению в номер документа
     */
//    public DtoResponse<DocumentHeaderDto> findByDpcumentHeader(RequestParametersForDocBody request) {
//        // getPageRequest(request.getOffset(), request.getLimit());
//        Collection<DocumentHeaderEntity> documentHeaders;
//        if (request.getDocumentHeaderId() > 0) {
//            documentHeaders = documentHeaderRepository.findByDocumentNumber(request.getQuery(), pageRequest);
//        } else {
//            throw new InvalidRequestParameteException(MESSAGE_BAD_REQUESR);
//        }
//        return new DtoResponse<>(documentHeaders.getTotalElements(),
//                mapperUtiliti.mapToCollectionDocumentHeaderDto(documentHeaders.getContent()));
//    }

    /**
     * удалить (переменную is_deleted в true)
     */
//    public void deleteById(long id) {
//        int countDelete = documentHeaderRepository.updateIsDeleteToTrueById(id);
//        if (countDelete <= 0) {
//            throw new NotFoundException(String.format(MESSAGE_NOT_FOUND, id));
//        }
//    }

    // добавить документ
//    public DocumentHeaderDto add(DocumentHeaderRequest request) {
//        DocumentHeaderEntity documentHeaderEntity = fillDocumentHeader(new DocumentHeaderEntity(), request);
//        return mapperUtiliti.mapToDocumentHeaderDto(documentHeaderRepository.save(documentHeaderEntity));
//    }

    // обновить документ
//    public DocumentHeaderDto update(long id, DocumentHeaderRequest request) {
//        DocumentHeaderEntity documentHeaderEntity = fillDocumentHeader(mapperUtiliti.mapToDocumentHeaderEntity(findById(id)), request);
//        return mapperUtiliti.mapToDocumentHeaderDto(documentHeaderRepository.save(documentHeaderEntity));
//    }

    // заполнить документ из запросса
//    private DocumentHeaderEntity fillDocumentHeader(DocumentHeaderEntity documentHeaderEntity, DocumentHeaderRequest request) {
//        documentHeaderEntity.setAmount(request.getAmount());
//        documentHeaderEntity.setComment(request.getComment());
//        documentHeaderEntity.setDate(request.getDate());
//        documentHeaderEntity.setIsDeleted(request.getIsDeleted());
//        documentHeaderEntity.setDocumentNumber(request.getDocumentNumber());
//        documentHeaderEntity.setIsRegistered(request.getIsRegistered());
//        documentHeaderEntity.setPartner(mapperUtiliti.mapToPartnerEntity(partnerService.findById(request.getPartnerId())));
//        documentHeaderEntity.setUser(mapperUtiliti.mapToUserEntity(userService.findById(request.getUserId())));
//        documentHeaderEntity.setWarehouse(mapperUtiliti.mapToWarehouseEntity(warehouseService.findById(request.getWarehouseId())));
//        if (request.getWarehouseRecipientId() != null) {
//            documentHeaderEntity.setWarehouseRecipient(mapperUtiliti.mapToWarehouseEntity(warehouseService.findById(request.getWarehouseRecipientId())));
//        }
//        documentHeaderEntity.setTypeDok(request.getTypeDok());
//
//        return documentHeaderEntity;
//    }


}
