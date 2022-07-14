package ru.home.inventoryaccounting.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.home.inventoryaccounting.domain.entity.UnitEntity;

@Repository
public interface UnitRepository extends JpaRepository<UnitEntity, Long> {

    // выбрать все помеченые на удаление записи
    Page<UnitEntity> getByIsDeletedTrue(Pageable pageable);

    // выбрать все неудаленные единицы измерения
    @Query("select u from UnitEntity u where u.isDeleted = false ")
    @Override
    Page<UnitEntity> findAll(Pageable pageable);

    // выбрать все неудаленные единицы измерения и вхождению в наименование
    @Query("select u from UnitEntity u where u.isDeleted=false and u.name like %:query% ")
    Page<UnitEntity> findByNameLike(Pageable pageable, String query);

    @Transactional
    @Modifying
    @Query("update UnitEntity set isDeleted=true where isDeleted=false and id=:id")
    int updateIsDeleteToTrueById(long id);
}
