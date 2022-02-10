package ru.home.inventoryaccounting.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.home.inventoryaccounting.domain.entity.WarehouseEntity;

@Repository
public interface WarehouseRepository extends JpaRepository<WarehouseEntity, Long> {

    // выбрать все неудаленные склады
    @Query("select w from WarehouseEntity w where w.isDeleted = false order by w.name")
    @Override
    Page<WarehouseEntity> findAll(Pageable pageable);

    // выбрать все неудаленные склады и вхождению в наименование
    @Query("select w from WarehouseEntity w where w.isDeleted=false and w.name like %:query% order by w.name")
    Page<WarehouseEntity> findByNameLike(Pageable pageable, String query);

}
