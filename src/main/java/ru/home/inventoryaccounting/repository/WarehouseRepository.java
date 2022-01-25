package ru.home.inventoryaccounting.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.home.inventoryaccounting.domain.entity.Warehouse;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    // выбрать все неудаленные склады
    @Query("select w from Warehouse w where w.deleted = false order by w.name")
    @Override
    Page<Warehouse> findAll(Pageable pageable);

    // выбрать все неудаленные склады и вхождению в наименование
    @Query("select w from Warehouse w where w.deleted=false and w.name like %:query% order by w.name")
    Page<Warehouse> findByNameLike(Pageable pageable, String query);

}
