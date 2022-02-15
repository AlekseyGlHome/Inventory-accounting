package ru.home.inventoryaccounting.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.home.inventoryaccounting.domain.entity.InventoryFolderEntity;
import ru.home.inventoryaccounting.domain.entity.WarehouseEntity;

import java.util.Optional;

@Repository
public interface InventoryFolderRepository extends JpaRepository<InventoryFolderEntity, Long> {

    // выбрать по id
    @Query("select i from InventoryFolderEntity i where i.isDeleted = false and i.id = :id")
    @Override
    Optional<InventoryFolderEntity> findById(Long id);

    // выбрать все неудаленные папки
    @Query("select i from InventoryFolderEntity i where i.isDeleted = false ")
    @Override
    Page<InventoryFolderEntity> findAll(Pageable pageable);

    // выбрать все неудаленные папки и вхождению в наименование
    @Query("select i from InventoryFolderEntity i where i.isDeleted=false and i.name like %:query% ")
    Page<InventoryFolderEntity> findByNameLike(Pageable pageable, String query);

    @Transactional
    @Modifying
    @Query("update InventoryFolderEntity set isDeleted=true where isDeleted=false and id=:id")
    int updateIsDeleteToTrueById(long id);

}