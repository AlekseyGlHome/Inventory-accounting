package ru.home.inventoryaccounting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.home.inventoryaccounting.domain.entity.DocumentHeaderEntity;
import ru.home.inventoryaccounting.domain.entity.DocumentRegistrationEntity;

public interface DocumentRegistryRepository extends JpaRepository<DocumentRegistrationEntity, Long> {

    long countByDocumentHeaderEntity(DocumentHeaderEntity documentHeaderEntity);

}
