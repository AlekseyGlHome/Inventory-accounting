package ru.home.inventoryaccounting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.home.inventoryaccounting.domain.entity.InventoryFolder;

@Repository
public interface InventoryFolderRepository extends JpaRepository<InventoryFolder, Long> {

}