package ru.home.inventoryaccounting.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.home.inventoryaccounting.domain.entity.PartnerEntity;
import ru.home.inventoryaccounting.domain.entity.WarehouseEntity;

import java.util.Optional;

@Repository
public interface PartnerRepository extends JpaRepository<PartnerEntity, Long> {

    // выбрать по id
    @Query("select p from PartnerEntity p where p.isDeleted = false and p.id = :id")
    @Override
    Optional<PartnerEntity> findById(Long id);

    // выбрать всех неудаленных партнеров
    @Query("select p from PartnerEntity p where p.isDeleted = false ")
    @Override
    Page<PartnerEntity> findAll(Pageable pageable);

    // выбрать всех неудаленных партнеров и вхождению в наименование
    @Query("select p from PartnerEntity p where p.isDeleted=false and p.name like %:query% ")
    Page<PartnerEntity> findByNameLike(Pageable pageable, String query);

    @Transactional
    @Modifying
    @Query("update PartnerEntity set isDeleted=true where isDeleted=false and id=:id")
    int updateIsDeleteToTrueById(long id);
}
