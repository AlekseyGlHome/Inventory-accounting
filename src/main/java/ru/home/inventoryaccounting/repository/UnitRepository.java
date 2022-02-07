package ru.home.inventoryaccounting.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.home.inventoryaccounting.domain.entity.UnitEntity;

@Repository
public interface UnitRepository extends JpaRepository<UnitEntity, Long> {

    // выбрать все неудаленные единицы измерения
    @Query("select u from UnitEntity u where u.deleted = false order by u.name")
    @Override
    Page<UnitEntity> findAll(Pageable pageable);

    // выбрать все неудаленные единицы измерения и вхождению в наименование
    @Query("select u from UnitEntity u where u.deleted=false and u.name like %:query% order by u.name")
    Page<UnitEntity> findByNameLike(Pageable pageable, String query);

}
