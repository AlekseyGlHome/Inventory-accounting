package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.response.DTOResponse;
import ru.home.inventoryaccounting.domain.dto.DocumentHeaderDto;
import ru.home.inventoryaccounting.domain.entity.DocumentHeaderEntity;
import ru.home.inventoryaccounting.domain.mapper.MapperUtiliti;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.DocumentsHeaderRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentsHeaderService {

    private final DocumentsHeaderRepository documentsHeaderRepository;
    private final MapperUtiliti mapperUtiliti;

    /**
     * выбор документа по идентификатору
     *
     * @param id - идентификатор документа
     * @return DocumentHeaderDTO
     * @throws NotFoundException
     */
    public DocumentHeaderDto findById(long id) throws NotFoundException {
        Optional<DocumentHeaderEntity> documentsHeader = documentsHeaderRepository.findById(id);
        return documentsHeader.map(mapperUtiliti::mapToDocumentHeaderDto)
                .orElseThrow(() -> new NotFoundException("Доумент с Id: " + id + " не найден."));
    }

    /**
     * выбор документов по вхождению в номер документа
     *
     * @param offset    - номер страницы
     * @param limit     - количество элементов на странице
     * @param numberStr - строка вхождения
     * @return DTOResponse&lt;DocumentHeaderDTO&gt;
     * @throws InvalidRequestParameteException
     */
    public DTOResponse<DocumentHeaderDto> findByDocumentNumber(int offset, int limit,
                                                               String numberStr) throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<DocumentHeaderEntity> documentHeaders;
        if (!numberStr.isEmpty() || !numberStr.isBlank()) {
            documentHeaders = documentsHeaderRepository.findByDocumentNumber(numberStr, pageRequest);
        } else {
            throw new InvalidRequestParameteException("Неверный параметр запроса");
        }
        return new DTOResponse<>(documentHeaders.getTotalElements(),
                mapperUtiliti.mapToCollectionDocumentHeaderDto(documentHeaders.getContent()));
    }

    /**
     * выбор документов за интервал по типу документа
     *
     * @param offset    - номер страницы
     * @param limit     - количество элементов на странице
     * @param dateStart - начало интервала
     * @param dateEnd   - окончание интервала
     * @param tipDok    - тип документа
     * @return DTOResponse&lt;DocumentHeaderDTO&gt;
     * @throws InvalidRequestParameteException
     */
    public DTOResponse<DocumentHeaderDto> findByDateAndTypeDok(int offset, int limit,
                                                               LocalDate dateStart,
                                                               LocalDate dateEnd,
                                                               Integer tipDok) throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<DocumentHeaderEntity> documentHeaders;
        if (tipDok > 0) {
            documentHeaders = documentsHeaderRepository.findByDateAndTypeDok(dateStart, dateEnd, tipDok, pageRequest);
        } else {
            throw new InvalidRequestParameteException("Неверный параметр запроса");
        }
        return new DTOResponse<>(documentHeaders.getTotalElements(),
                mapperUtiliti.mapToCollectionDocumentHeaderDto(documentHeaders.getContent()));
    }

    /**
     * выбор документов за интервал по типу документа и по складу
     *
     * @param offset      - номер страницы
     * @param limit       - количество элементов на странице
     * @param dateStart   - начало интервала
     * @param dateEnd     - окончание интервала
     * @param tipDok      - тип документа
     * @param warehouseId - идентификатор склада
     * @return DTOResponse&lt;DocumentHeaderDTO&gt;
     * @throws InvalidRequestParameteException
     */
    public DTOResponse<DocumentHeaderDto> findByDateAndTypeDokAndWarehouse(int offset, int limit,
                                                                           LocalDate dateStart,
                                                                           LocalDate dateEnd,
                                                                           Integer tipDok,
                                                                           long warehouseId) throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<DocumentHeaderEntity> documentHeaders;
        if (tipDok > 0 && warehouseId > 0) {
            documentHeaders = documentsHeaderRepository
                    .findByDateAndTypeDokAndWarehouse(dateStart, dateEnd, tipDok, warehouseId, pageRequest);
        } else {
            throw new InvalidRequestParameteException("Неверный параметр запроса");
        }
        return new DTOResponse<>(documentHeaders.getTotalElements(),
                mapperUtiliti.mapToCollectionDocumentHeaderDto(documentHeaders.getContent()));
    }

    /**
     * выбор документов за интервал и по партнеру
     *
     * @param offset    - номер страницы
     * @param limit     - количество элементов на странице
     * @param dateStart - начало интервала
     * @param dateEnd   - окончание интервала
     * @param partnerId - идентификатор партнера(контрагента)
     * @return DTOResponse&lt;DocumentHeaderDTO&gt;
     * @throws InvalidRequestParameteException
     */
    public DTOResponse<DocumentHeaderDto> findByDateAndPartner(int offset, int limit,
                                                               LocalDate dateStart,
                                                               LocalDate dateEnd,
                                                               long partnerId) throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<DocumentHeaderEntity> documentHeaders;
        if (partnerId > 0) {
            documentHeaders = documentsHeaderRepository.findByDateAndPartner(dateStart, dateEnd, partnerId, pageRequest);
        } else {
            throw new InvalidRequestParameteException("Неверный параметр запроса");
        }
        return new DTOResponse<>(documentHeaders.getTotalElements(),
                mapperUtiliti.mapToCollectionDocumentHeaderDto(documentHeaders.getContent()));
    }

    /**
     * выбор документов за интервал
     *
     * @param offset    - номер страницы
     * @param limit     - количество элементов на странице
     * @param dateStart - начало интервала
     * @param dateEnd   - окончание интервала
     * @return DTOResponse&lt;DocumentHeaderDTO&gt;
     */
    public DTOResponse<DocumentHeaderDto> findByDate(int offset, int limit,
                                                     LocalDate dateStart,
                                                     LocalDate dateEnd) {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<DocumentHeaderEntity> documentHeaders;
        documentHeaders = documentsHeaderRepository.findByDate(dateStart, dateEnd, pageRequest);
        return new DTOResponse<>(documentHeaders.getTotalElements(),
                mapperUtiliti.mapToCollectionDocumentHeaderDto(documentHeaders.getContent()));
    }

    /**
     * выбор документов за интервал и по складу
     *
     * @param offset      - номер страницы
     * @param limit       - количество элементов на странице
     * @param dateStart   - начало интервала
     * @param dateEnd     - окончание интервала
     * @param warehouseId - идентификатор склада
     * @return DTOResponse&lt;DocumentHeaderDTO&gt;
     * @throws InvalidRequestParameteException
     */
    public DTOResponse<DocumentHeaderDto> findByDateAndWarehouse(int offset, int limit,
                                                                 LocalDate dateStart,
                                                                 LocalDate dateEnd,
                                                                 long warehouseId) throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<DocumentHeaderEntity> documentHeaders;
        if (warehouseId > 0) {
            documentHeaders = documentsHeaderRepository.findByDateAndWarehouse(dateStart, dateEnd, warehouseId, pageRequest);
        } else {
            throw new InvalidRequestParameteException("Неверный параметр запроса");
        }
        return new DTOResponse<>(documentHeaders.getTotalElements(),
                mapperUtiliti.mapToCollectionDocumentHeaderDto(documentHeaders.getContent()));
    }

    /**
     * выбор документов за интервал по партнеру и по складу
     *
     * @param offset      - номер страницы
     * @param limit       - количество элементов на странице
     * @param dateStart   - начало интервала
     * @param dateEnd     - окончание интервала
     * @param partnerId   - идентификатор партнера(контрагента)
     * @param warehouseId - идентификатор склада
     * @return DTOResponse&lt;DocumentHeaderDTO&gt;
     * @throws InvalidRequestParameteException
     */
    public DTOResponse<DocumentHeaderDto> findByDateAndPartnerAndWarehouse(int offset, int limit,
                                                                           LocalDate dateStart,
                                                                           LocalDate dateEnd,
                                                                           long partnerId,
                                                                           long warehouseId) throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<DocumentHeaderEntity> documentHeaders;
        if (warehouseId > 0 && partnerId > 0) {
            documentHeaders = documentsHeaderRepository
                    .findByDateAndPartnerAndWarehouse(dateStart, dateEnd, partnerId, warehouseId, pageRequest);
        } else {
            throw new InvalidRequestParameteException("Неверный параметр запроса");
        }
        return new DTOResponse<>(documentHeaders.getTotalElements(),
                mapperUtiliti.mapToCollectionDocumentHeaderDto(documentHeaders.getContent()));
    }

    // создать страницу пагинации
    private PageRequest getPageRequest(int offset, int limit) {
        int numberPage = offset / limit;

        return PageRequest.of(numberPage, limit);
    }
}
