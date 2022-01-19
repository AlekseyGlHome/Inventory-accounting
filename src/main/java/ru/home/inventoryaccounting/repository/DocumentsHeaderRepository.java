package ru.home.inventoryaccounting.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.home.inventoryaccounting.domain.entity.DocumentsHeader;

import java.time.LocalDate;

@Repository
public interface DocumentsHeaderRepository extends JpaRepository<DocumentsHeader, Long> {
    // выбор документов за интервал
    @Query("select d from DocumentsHeader d where d.deleted=false and d.date between :dateStart and :dateEnd order by d.date")
    Page<DocumentsHeader> findByDate(LocalDate dateStart, LocalDate dateEnd, Pageable pageable);

    // выбор документов за интервал и по партнеру
    @Query("select d from DocumentsHeader d where d.date between :dateStart and :dateEnd " +
            "and d.deleted = false and d.partner.id = :partnerId order by d.date")
    Page<DocumentsHeader> findByDateAndPartner(LocalDate dateStart, LocalDate dateEnd, Long partnerId, Pageable pageable);

    // выбор документов за интервал и по складу
    @Query("select d from DocumentsHeader d where d.date between :dateStart and :dateEnd " +
            "and d.deleted = false and (d.warehouse.id = :warehouseId or d.warehouseRecipient.id = :warehouseId) order by d.date")
    Page<DocumentsHeader> findByDateAndWarehouse(LocalDate dateStart, LocalDate dateEnd, Long warehouseId, Pageable pageable);

    // выбор документов за интервал по партнеру и по складу
    @Query("select d from DocumentsHeader d where d.date between :dateStart and :dateEnd " +
            "and d.deleted = false and d.partner.id = :partnerId " +
            "and (d.warehouse.id = :warehouseId or d.warehouseRecipient.id = :warehouseId) order by d.date")
    Page<DocumentsHeader> findByDateAndPartnerAndWarehouse(LocalDate dateStart, LocalDate dateEnd,
                                                           Long partnerId, Long warehouseId,
                                                           Pageable pageable);

    // выбор документов за интервал по типу документа
    @Query("select d from DocumentsHeader d where d.date between :dateStart and :dateEnd " +
            "and d.deleted = false and d.typeDok = :typeDok order by d.date")
    Page<DocumentsHeader> findByDateAndTypeDok(LocalDate dateStart, LocalDate dateEnd, Integer typeDok, Pageable pageable);

    // выбор документов за интервал по типу документа и по складу
    @Query("select d from DocumentsHeader d where d.date between :dateStart and :dateEnd " +
            "and d.deleted = false and d.typeDok = :typeDok " +
            "and (d.warehouse.id = :warehouseId or d.warehouseRecipient.id = :warehouseId) order by d.date")
    Page<DocumentsHeader> findByDateAndTypeDokAndWarehouse(LocalDate dateStart, LocalDate dateEnd,
                                                           Integer typeDok, Long warehouseId,
                                                           Pageable pageable);

    // выбор документов по вхождению в номер документа
    @Query("select d from DocumentsHeader d where d.deleted = false and d.documentNumber like %:documentNumber% order by d.date")
    Page<DocumentsHeader> findByDocumentNumber(String documentNumber, Pageable pageable);


}
