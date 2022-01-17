package ru.home.inventoryaccounting.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.home.inventoryaccounting.domain.entity.InventoryFolder;

@Repository
public interface InventoryFolderRepository extends JpaRepository<InventoryFolder, Long> {

    @Query("select i from InventoryFolder i where i.deleted = false order by i.name")
    Page<InventoryFolder> findByDeletedFalse(Pageable pageable);

    @Query("select i from InventoryFolder i where i.deleted=false and i.name like %:query% order by i.name")
    Page<InventoryFolder> findByNameLike(Pageable pageable, String query);

}