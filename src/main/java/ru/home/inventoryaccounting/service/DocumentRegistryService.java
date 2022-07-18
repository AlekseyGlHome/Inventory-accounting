package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.home.inventoryaccounting.domain.entity.DocumentHeaderEntity;
import ru.home.inventoryaccounting.domain.entity.DocumentRegistrationEntity;
import ru.home.inventoryaccounting.repository.DocumentRegistryRepository;

@Service
@RequiredArgsConstructor
public class DocumentRegistryService {

    private final DocumentRegistryRepository documentRegistryRepository;
    private final DocumentService documentService;

    @Transactional
    public void addToRegistration(Long id) {
        DocumentHeaderEntity documentHeaderEntity = documentService.getById(id);

        long countRegDoc = documentRegistryRepository.countByDocumentHeaderEntity(documentHeaderEntity);
        if (countRegDoc <= 0) {
            DocumentRegistrationEntity documentRegistrationEntity = new DocumentRegistrationEntity(documentHeaderEntity);
            documentRegistryRepository.save(documentRegistrationEntity);
        }
    }

}
