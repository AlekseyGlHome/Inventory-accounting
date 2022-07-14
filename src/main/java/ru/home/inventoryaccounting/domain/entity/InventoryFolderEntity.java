package ru.home.inventoryaccounting.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.home.inventoryaccounting.domain.dto.InventoryFolderDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "inventory_folder")
public class InventoryFolderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "name", nullable = false, length = 250)
    private String name;

    @OneToMany(mappedBy = "folder", fetch = FetchType.LAZY)
    private Collection<InventoryEntity> inventories = new ArrayList<>();

    public InventoryFolderEntity(InventoryFolderDto inventoryFolderDto) {
        setId(inventoryFolderDto.getId());
        setIsDeleted(inventoryFolderDto.getIsDeleted());
        setName(inventoryFolderDto.getName());
    }

}