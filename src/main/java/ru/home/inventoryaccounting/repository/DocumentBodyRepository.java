package ru.home.inventoryaccounting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.home.inventoryaccounting.domain.entity.DocumentBodyEntity;

import java.util.List;

@Repository
public interface DocumentBodyRepository extends JpaRepository<DocumentBodyEntity, Long> {

    // получить DocumentBody по идентификатору DocumentHeader
    @Query("select d from DocumentBodyEntity d where d.documentHeader.id = :headerId")
    List<DocumentBodyEntity> findByDocumentHeader_IdEquals(Long headerId);



}
