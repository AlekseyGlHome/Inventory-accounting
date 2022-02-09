package ru.home.inventoryaccounting.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.home.inventoryaccounting.domain.entity.DocumentHeaderEntity;

import java.time.LocalDate;

@Repository
public interface DocumentHeaderRepository extends JpaRepository<DocumentHeaderEntity, Long> {
    // выбор документов за интервал
    @Query("select d from DocumentHeaderEntity d where d.deleted=false and d.date between :dateStart and :dateEnd order by d.date")
    Page<DocumentHeaderEntity> findByDate(LocalDate dateStart, LocalDate dateEnd, Pageable pageable);

    // выбор документов за интервал и по партнеру
    @Query("select d from DocumentHeaderEntity d where d.date between :dateStart and :dateEnd " +
            "and d.deleted = false and d.partner.id = :partnerId order by d.date")
    Page<DocumentHeaderEntity> findByDateAndPartner(LocalDate dateStart, LocalDate dateEnd, Long partnerId, Pageable pageable);

    // выбор документов за интервал и по складу
    @Query("select d from DocumentHeaderEntity d where d.date between :dateStart and :dateEnd " +
            "and d.deleted = false and (d.warehouse.id = :warehouseId or d.warehouseRecipient.id = :warehouseId) order by d.date")
    Page<DocumentHeaderEntity> findByDateAndWarehouse(LocalDate dateStart, LocalDate dateEnd, Long warehouseId, Pageable pageable);

    // выбор документов за интервал по партнеру и по складу
    @Query("select d from DocumentHeaderEntity d where d.date between :dateStart and :dateEnd " +
            "and d.deleted = false and d.partner.id = :partnerId " +
            "and (d.warehouse.id = :warehouseId or d.warehouseRecipient.id = :warehouseId) order by d.date")
    Page<DocumentHeaderEntity> findByDateAndPartnerAndWarehouse(LocalDate dateStart, LocalDate dateEnd,
                                                                Long partnerId, Long warehouseId,
                                                                Pageable pageable);

    // выбор документов за интервал по типу документа
    @Query("select d from DocumentHeaderEntity d where d.date between :dateStart and :dateEnd " +
            "and d.deleted = false and d.typeDok = :typeDok order by d.date")
    Page<DocumentHeaderEntity> findByDateAndTypeDok(LocalDate dateStart, LocalDate dateEnd, Integer typeDok, Pageable pageable);

    // выбор документов за интервал по типу документа и по складу
    @Query("select d from DocumentHeaderEntity d where d.date between :dateStart and :dateEnd " +
            "and d.deleted = false and d.typeDok = :typeDok " +
            "and (d.warehouse.id = :warehouseId or d.warehouseRecipient.id = :warehouseId) order by d.date")
    Page<DocumentHeaderEntity> findByDateAndTypeDokAndWarehouse(LocalDate dateStart, LocalDate dateEnd,
                                                                Integer typeDok, Long warehouseId,
                                                                Pageable pageable);

    // выбор документов по вхождению в номер документа
    @Query("select d from DocumentHeaderEntity d where d.deleted = false and d.documentNumber like %:documentNumber% order by d.date")
    Page<DocumentHeaderEntity> findByDocumentNumber(String documentNumber, Pageable pageable);


}
