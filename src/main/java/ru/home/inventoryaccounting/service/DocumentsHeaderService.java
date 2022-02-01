package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.response.DTOResponse;
import ru.home.inventoryaccounting.domain.DTO.DocumentHeaderDTO;
import ru.home.inventoryaccounting.domain.entity.DocumentHeader;
import ru.home.inventoryaccounting.domain.mapper.DocumentHeaderMapper;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.DocumentsHeaderRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentsHeaderService {

    private final DocumentsHeaderRepository documentsHeaderRepository;
    private final DocumentHeaderMapper documentHeaderMapper;

    /**
     * выбор документа по идентификатору
     *
     * @param id - идентификатор документа
     * @return DocumentHeaderDTO
     * @throws NotFoundException
     */
    public DocumentHeaderDTO findById(long id) throws NotFoundException {
        Optional<DocumentHeader> documentsHeader = documentsHeaderRepository.findById(id);
        return documentsHeader.map(documentHeaderMapper::convertToDTO)
                .orElseThrow(() -> new NotFoundException("Запись с Id: " + id + " не найдена."));
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
    public DTOResponse<DocumentHeaderDTO> findByDocumentNumber(int offset, int limit,
                                                               String numberStr) throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<DocumentHeader> documentHeaders;
        if (!numberStr.isEmpty() || !numberStr.isBlank()) {
            documentHeaders = documentsHeaderRepository.findByDocumentNumber(numberStr, pageRequest);
        } else {
            throw new InvalidRequestParameteException("Неверный параметр запроса");
        }
        return new DTOResponse<>(documentHeaders.getTotalElements(),
                documentHeaderMapper.convertCollectionToDTO(documentHeaders.getContent()));
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
    public DTOResponse<DocumentHeaderDTO> findByDateAndTypeDok(int offset, int limit,
                                                               LocalDate dateStart,
                                                               LocalDate dateEnd,
                                                               Integer tipDok) throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<DocumentHeader> documentHeaders;
        if (tipDok > 0) {
            documentHeaders = documentsHeaderRepository.findByDateAndTypeDok(dateStart, dateEnd, tipDok, pageRequest);
        } else {
            throw new InvalidRequestParameteException("Неверный параметр запроса");
        }
        return new DTOResponse<>(documentHeaders.getTotalElements(),
                documentHeaderMapper.convertCollectionToDTO(documentHeaders.getContent()));
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
    public DTOResponse<DocumentHeaderDTO> findByDateAndTypeDokAndWarehouse(int offset, int limit,
                                                                           LocalDate dateStart,
                                                                           LocalDate dateEnd,
                                                                           Integer tipDok,
                                                                           long warehouseId) throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<DocumentHeader> documentHeaders;
        if (tipDok > 0 && warehouseId > 0) {
            documentHeaders = documentsHeaderRepository
                    .findByDateAndTypeDokAndWarehouse(dateStart, dateEnd, tipDok, warehouseId, pageRequest);
        } else {
            throw new InvalidRequestParameteException("Неверный параметр запроса");
        }
        return new DTOResponse<>(documentHeaders.getTotalElements(),
                documentHeaderMapper.convertCollectionToDTO(documentHeaders.getContent()));
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
    public DTOResponse<DocumentHeaderDTO> findByDateAndPartner(int offset, int limit,
                                                               LocalDate dateStart,
                                                               LocalDate dateEnd,
                                                               long partnerId) throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<DocumentHeader> documentHeaders;
        if (partnerId > 0) {
            documentHeaders = documentsHeaderRepository.findByDateAndPartner(dateStart, dateEnd, partnerId, pageRequest);
        } else {
            throw new InvalidRequestParameteException("Неверный параметр запроса");
        }
        return new DTOResponse<>(documentHeaders.getTotalElements(),
                documentHeaderMapper.convertCollectionToDTO(documentHeaders.getContent()));
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
    public DTOResponse<DocumentHeaderDTO> findByDate(int offset, int limit,
                                                     LocalDate dateStart,
                                                     LocalDate dateEnd) {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<DocumentHeader> documentHeaders;
        documentHeaders = documentsHeaderRepository.findByDate(dateStart, dateEnd, pageRequest);
        return new DTOResponse<>(documentHeaders.getTotalElements(),
                documentHeaderMapper.convertCollectionToDTO(documentHeaders.getContent()));
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
    public DTOResponse<DocumentHeaderDTO> findByDateAndWarehouse(int offset, int limit,
                                                                 LocalDate dateStart,
                                                                 LocalDate dateEnd,
                                                                 long warehouseId) throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<DocumentHeader> documentHeaders;
        if (warehouseId > 0) {
            documentHeaders = documentsHeaderRepository.findByDateAndWarehouse(dateStart, dateEnd, warehouseId, pageRequest);
        } else {
            throw new InvalidRequestParameteException("Неверный параметр запроса");
        }
        return new DTOResponse<>(documentHeaders.getTotalElements(),
                documentHeaderMapper.convertCollectionToDTO(documentHeaders.getContent()));
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
    public DTOResponse<DocumentHeaderDTO> findByDateAndPartnerAndWarehouse(int offset, int limit,
                                                                           LocalDate dateStart,
                                                                           LocalDate dateEnd,
                                                                           long partnerId,
                                                                           long warehouseId) throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<DocumentHeader> documentHeaders;
        if (warehouseId > 0 && partnerId > 0) {
            documentHeaders = documentsHeaderRepository
                    .findByDateAndPartnerAndWarehouse(dateStart, dateEnd, partnerId, warehouseId, pageRequest);
        } else {
            throw new InvalidRequestParameteException("Неверный параметр запроса");
        }
        return new DTOResponse<>(documentHeaders.getTotalElements(),
                documentHeaderMapper.convertCollectionToDTO(documentHeaders.getContent()));
    }

    // создать страницу пагинации
    private PageRequest getPageRequest(int offset, int limit) {
        int numberPage = offset / limit;

        return PageRequest.of(numberPage, limit);
    }
}
