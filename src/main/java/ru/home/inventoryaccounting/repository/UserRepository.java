package ru.home.inventoryaccounting.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.home.inventoryaccounting.domain.entity.UserEntity;
import ru.home.inventoryaccounting.domain.entity.WarehouseEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // выбрать все помеченые на удаление записи
    Page<UserEntity> getByIsDeletedTrue(Pageable pageable);

    // выбрать всех неудаленных пользователей
    @Query("select u from UserEntity u where u.isDeleted = false ")
    @Override
    Page<UserEntity> findAll(Pageable pageable);

    //// выбрать всех неудаленных пользователей и вхождению в наименование
    @Query("select u from UserEntity u where u.isDeleted=false and u.name like %:query% ")
    Page<UserEntity> findByNameLike(Pageable pageable, String query);

    //выбрать пользователя по имени
    @Query("select u from UserEntity u where u.isDeleted=false and u.name = :query ")
    Optional<UserEntity> findByName(String query);

    @Transactional
    @Modifying
    @Query("update UserEntity set isDeleted=true where isDeleted=false and id=:id")
    int updateIsDeleteToTrueById(long id);
}
