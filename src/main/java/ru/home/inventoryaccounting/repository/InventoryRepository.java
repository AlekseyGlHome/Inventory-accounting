package ru.home.inventoryaccounting.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.home.inventoryaccounting.domain.entity.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    // выбрать весь неудаленный инвентарь
    @Query("select i from Inventory i where i.deleted = false")
    @Override
    Page<Inventory> findAll(Pageable pageable);

    // выбрать весь неудаленный инвентарь и вхождению в наименование
    @Query("select i from Inventory i where i.deleted=false and i.name like %:query% order by i.name")
    Page<Inventory> findByNameLike(Pageable pageable, String query);

    // выбрать весь неудаленный инвентарь и по идентификатору группы
    @Query("select i from Inventory i where i.deleted=false and i.folder.id = :id order by i.name")
    Page<Inventory> findByFolderId(Pageable pageable, Long id);

    // выбрать весь неудаленный инвентарь и вхождению в наименование и по идентификатору группы
    @Query("select i from Inventory i where i.deleted=false and i.name like %:query% " +
            "and i.folder.id = :folderId order by i.name")
    Page<Inventory> findByNameLikeAndFolderId(Pageable pageable, String query, Long folderId);

}
