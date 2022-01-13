package ru.home.inventoryaccounting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.home.inventoryaccounting.domain.entity.DocumentsHeader;

@Repository
public interface DocumentsHeaderRepository extends JpaRepository<DocumentsHeader, Long> {
}
