package ru.home.inventoryaccounting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.home.inventoryaccounting.domain.entity.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
