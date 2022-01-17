package ru.home.inventoryaccounting.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.home.inventoryaccounting.domain.entity.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query("select i from Inventory i where i.deleted = false order by i.name")
    Page<Inventory> findByDeletedFalse(Pageable pageable);

    @Query("select i from Inventory i where i.deleted=false and i.name like %:query% order by i.name")
    Page<Inventory> findByNameLike(Pageable pageable, String query);

    @Query("select i from Inventory i where i.deleted=false and i.folder.id = :id order by i.name")
    Page<Inventory> findByFolder_IdEquals(Pageable pageable, Long id);

    @Query("select i from Inventory i where i.deleted=false and i.name like %:query% " +
            "and i.folder.id = :folderId order by i.name")
    Page<Inventory> findByNameLikeAndFolder_IdEquals(Pageable pageable, String query, Long folderId);

}
