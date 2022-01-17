package ru.home.inventoryaccounting.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.home.inventoryaccounting.domain.entity.Partner;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {

    @Query("select p from Partner p where p.deleted = false order by p.name")
    Page<Partner> findByDeletedFalse(Pageable pageable);

    @Query("select p from Partner p where p.deleted=false and p.name like %:query% order by p.name")
    Page<Partner> findByNameLike(Pageable pageable, String query);

}