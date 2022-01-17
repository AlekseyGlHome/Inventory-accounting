package ru.home.inventoryaccounting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.home.inventoryaccounting.domain.entity.Movement;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {

}
