package ru.home.inventoryaccounting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.home.inventoryaccounting.domain.entity.DocumentBody;

import java.util.List;

@Repository
public interface DocumentsBodyRepository extends JpaRepository<DocumentBody, Long> {

    // получить DocumentBody по идентификатору DocumentHeader
    @Query("select d from DocumentBody d where d.documentHeader.id = :headerId")
    List<DocumentBody> findByDocumentHeader_IdEquals(Long headerId);



}
