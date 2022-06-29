package ru.home.inventoryaccounting.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.home.inventoryaccounting.domain.entity.DocumentHeaderEntity;
import ru.home.inventoryaccounting.domain.entity.WarehouseEntity;

import java.time.LocalDate;

@Repository
public interface DocumentHeaderRepository extends JpaRepository<DocumentHeaderEntity, Long> {
    // выбор документов за интервал
    @Query("select d from DocumentHeaderEntity d where d.isDeleted=false and d.date between :dateStart and :dateEnd ")
    Page<DocumentHeaderEntity> findByDate(LocalDate dateStart, LocalDate dateEnd, Pageable pageable);

    // выбор документов за интервал и по партнеру
    @Query("select d from DocumentHeaderEntity d where d.date between :dateStart and :dateEnd " +
            "and d.isDeleted = false and d.partner.id = :partnerId ")
    Page<DocumentHeaderEntity> findByDateAndPartner(LocalDate dateStart, LocalDate dateEnd, Long partnerId, Pageable pageable);

    // выбор документов за интервал и по партнеру и типу документа
    @Query("select d from DocumentHeaderEntity d where d.date between :dateStart and :dateEnd " +
            "and d.isDeleted = false and d.partner.id = :partnerId and d.typeDok=:typeDok ")
    Page<DocumentHeaderEntity> findByDateAndPartnerAndTypeDok(LocalDate dateStart, LocalDate dateEnd, Long partnerId, Integer typeDok, Pageable pageable);

    // выбор документов за интервал и по складу
//    @Query("select d from DocumentHeaderEntity d where d.date between :dateStart and :dateEnd " +
//            "and d.isDeleted = false and (d.warehouse.id = :warehouseId or d.warehouseRecipient.id = :warehouseId) ")
//    Page<DocumentHeaderEntity> findByDateAndWarehouse(LocalDate dateStart, LocalDate dateEnd, Long warehouseId, Pageable pageable);

    // выбор документов за интервал и по складу
    @Query("""
            select d from DocumentHeaderEntity d
            where d.isDeleted = false and d.date>=:dateStart AND d.date<=:dateEnd and (d.warehouse.id=:warehouseId or d.warehouseRecipient.id=:warehouseId)""")
    Page<DocumentHeaderEntity> findByDateBetweenAndWarehouseOrWarehouseRecipient(
            @Param("dateStart") LocalDate dateStart,
            @Param("dateEnd") LocalDate dateEnd,
            @Param("warehouseId") Long warehouseId,
            Pageable pageable);


    // выбор документов за интервал по партнеру и по складу
    @Query("select d from DocumentHeaderEntity d where d.date between :dateStart and :dateEnd " +
            "and d.isDeleted = false and d.partner.id = :partnerId " +
            "and (d.warehouse.id = :warehouseId or d.warehouseRecipient.id = :warehouseId) ")
    Page<DocumentHeaderEntity> findByDateAndPartnerAndWarehouse(LocalDate dateStart, LocalDate dateEnd,
                                                                Long partnerId, Long warehouseId,
                                                                Pageable pageable);

    // выбор документов за интервал по типу документа
    @Query("select d from DocumentHeaderEntity d where d.date between :dateStart and :dateEnd " +
            "and d.isDeleted = false and d.typeDok = :typeDok ")
    Page<DocumentHeaderEntity> findByDateAndTypeDok(LocalDate dateStart, LocalDate dateEnd, Integer typeDok, Pageable pageable);

    // выбор документов за интервал по типу документа и по складу
    @Query("select d from DocumentHeaderEntity d where d.date between :dateStart and :dateEnd " +
            "and d.isDeleted = false and d.typeDok = :typeDok " +
            "and (d.warehouse.id = :warehouseId or d.warehouseRecipient.id = :warehouseId) ")
    Page<DocumentHeaderEntity> findByDateAndTypeDokAndWarehouse(LocalDate dateStart, LocalDate dateEnd,
                                                                Integer typeDok, Long warehouseId,
                                                                Pageable pageable);

    // выбор документов по вхождению в номер документа
    @Query("select d from DocumentHeaderEntity d where d.isDeleted = false and d.documentNumber like %:documentNumber% ")
    Page<DocumentHeaderEntity> findByDocumentNumber(String documentNumber, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update DocumentHeaderEntity set isDeleted=true where isDeleted=false and id=:id")
    int updateIsDeleteToTrueById(long id);

}
