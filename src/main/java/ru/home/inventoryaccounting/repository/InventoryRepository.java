package ru.home.inventoryaccounting.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.home.inventoryaccounting.domain.entity.InventoryEntity;
import ru.home.inventoryaccounting.domain.entity.UserEntity;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {

    // выбрать все помеченые на удаление записи
    Page<InventoryEntity> getByIsDeletedTrue(Pageable pageable);

    // выбрать весь неудаленный инвентарь
    @Query("select i from InventoryEntity i where i.isDeleted = false")
    @Override
    Page<InventoryEntity> findAll(Pageable pageable);

    // выбрать весь неудаленный инвентарь и вхождению в наименование
    @Query("select i from InventoryEntity i where i.isDeleted=false and i.name like %:query% ")
    Page<InventoryEntity> findByNameLike(Pageable pageable, String query);

    // выбрать весь неудаленный инвентарь и по идентификатору группы
    @Query("select i from InventoryEntity i where i.isDeleted=false and i.folder.id = :id ")
    Page<InventoryEntity> findByFolderId(Pageable pageable, Long id);

    // выбрать весь неудаленный инвентарь и вхождению в наименование и по идентификатору группы
    @Query("select i from InventoryEntity i where i.isDeleted=false and i.name like %:query% " +
            "and i.folder.id = :folderId ")
    Page<InventoryEntity> findByNameLikeAndFolderId(Pageable pageable, String query, Long folderId);

    @Transactional
    @Modifying
    @Query("update InventoryEntity set isDeleted=true where isDeleted=false and id=:id")
    int updateIsDeleteToTrueById(long id);

}
