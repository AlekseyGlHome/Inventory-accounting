package ru.home.inventoryaccounting.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.home.inventoryaccounting.domain.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // выбрать всех неудаленных пользователей
    @Query("select u from UserEntity u where u.isDeleted = false order by u.name")
    @Override
    Page<UserEntity> findAll(Pageable pageable);

    //// выбрать всех неудаленных пользователей и вхождению в наименование
    @Query("select u from UserEntity u where u.isDeleted=false and u.name like %:query% order by u.name")
    Page<UserEntity> findByNameLike(Pageable pageable, String query);

    //выбрать пользователя по имени
    @Query("select u from UserEntity u where u.isDeleted=false and u.name = :query order by u.name")
    Optional<UserEntity> findByName(String query);

}
