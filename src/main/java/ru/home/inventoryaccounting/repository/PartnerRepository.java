package ru.home.inventoryaccounting.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.home.inventoryaccounting.domain.entity.PartnerEntity;

@Repository
public interface PartnerRepository extends JpaRepository<PartnerEntity, Long> {

    // выбрать всех неудаленных партнеров
    @Query("select p from PartnerEntity p where p.deleted = false order by p.name")
    @Override
    Page<PartnerEntity> findAll(Pageable pageable);

    // выбрать всех неудаленных партнеров и вхождению в наименование
    @Query("select p from PartnerEntity p where p.deleted=false and p.name like %:query% order by p.name")
    Page<PartnerEntity> findByNameLike(Pageable pageable, String query);

}
