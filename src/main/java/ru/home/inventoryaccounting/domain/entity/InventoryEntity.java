package ru.home.inventoryaccounting.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.home.inventoryaccounting.domain.dto.InventoryDto;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "inventory")
public class InventoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "name", nullable = false, length = 250)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "folder_id", nullable = false)
    private InventoryFolderEntity folder;

    @ManyToOne(optional = false)
    @JoinColumn(name = "unit_id", nullable = false)
    private UnitEntity unit;

    public InventoryEntity(InventoryDto inventoryDto) {
        setId(inventoryDto.getId());
        setIsDeleted(inventoryDto.isDeleted());
        setName(inventoryDto.getName());
        setFolder(new InventoryFolderEntity(inventoryDto.getFolder()));
        setUnit(new UnitEntity(inventoryDto.getUnit()));
    }

}