package ru.home.inventoryaccounting.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.home.inventoryaccounting.domain.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.deleted = false order by u.name")
    Page<User> findByDeletedFalse(Pageable pageable);

    @Query("select u from User u where u.deleted=false and u.name like %:query% order by u.name")
    Page<User> findByNameLike(Pageable pageable, String query);

    @Query("select u from User u where u.deleted=false and u.name = :query order by u.name")
    Page<User> findByName(Pageable pageable, String query);

}
