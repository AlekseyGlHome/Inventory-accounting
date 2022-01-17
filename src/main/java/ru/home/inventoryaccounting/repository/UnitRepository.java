package ru.home.inventoryaccounting.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.home.inventoryaccounting.domain.entity.Unit;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

    @Query("select u from Unit u where u.deleted = false order by u.name")
    Page<Unit> findByDeletedFalse(Pageable pageable);

    @Query("select u from Unit u where u.deleted=false and u.name like %:query% order by u.name")
    Page<Unit> findByNameLike(Pageable pageable, String query);

}
