package ru.home.inventoryaccounting.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.home.inventoryaccounting.domain.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // выбрать всех неудаленных пользователей
    @Query("select u from User u where u.deleted = false order by u.name")
    @Override
    Page<User> findAll(Pageable pageable);

    //// выбрать всех неудаленных пользователей и вхождению в наименование
    @Query("select u from User u where u.deleted=false and u.name like %:query% order by u.name")
    Page<User> findByNameLike(Pageable pageable, String query);

    //выбрать пользователя по имени
    @Query("select u from User u where u.deleted=false and u.name = :query order by u.name")
    Optional<User> findByName(String query);

}
