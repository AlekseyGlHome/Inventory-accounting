package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.response.DTOResponse;
import ru.home.inventoryaccounting.domain.DTO.DocumentHeaderDTO;
import ru.home.inventoryaccounting.domain.DTO.InventoryFolderDTO;
import ru.home.inventoryaccounting.domain.entity.DocumentHeader;
import ru.home.inventoryaccounting.domain.entity.InventoryFolder;
import ru.home.inventoryaccounting.domain.mapper.DocumentHeaderMapper;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.DocumentsHeaderRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentsHeaderService {

    private final DocumentsHeaderRepository documentsHeaderRepository;
    private final DocumentHeaderMapper documentHeaderMapper;

    public DocumentHeaderDTO findById(long id) throws NotFoundException {
        Optional<DocumentHeader> documentsHeader = documentsHeaderRepository.findById(id);
        return documentsHeader.map(documentHeaderMapper::documentHeaderToDTO)
                .orElseThrow(() -> new NotFoundException("Запись с Id: " + id + " не найдена."));
    }

    public DTOResponse<DocumentHeaderDTO> findByQueryString(int offset, int limit, String query) {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<DocumentHeader> documentHeaders;
        if (!query.isEmpty() || !query.isBlank()) {
            documentHeaders = documentsHeaderRepository.findByDocumentNumber(query, pageRequest);
        } else {
            documentHeaders = documentsHeaderRepository.findAll(pageRequest);
        }

        return new DTOResponse<>(documentHeaders.getTotalElements(), getDocumentHeaderDTOS(documentHeaders));
    }


    private List<DocumentHeaderDTO> getDocumentHeaderDTOS(Page<DocumentHeader> documentHeaders) {
        return documentHeaders.getContent().stream()
                .map(documentHeaderMapper::documentHeaderToDTO)
                .collect(Collectors.toList());
    }

    private PageRequest getPageRequest(int offset, int limit) {
        int numberPage = offset / limit;

        return PageRequest.of(numberPage, limit);
    }
}
