package ru.home.inventoryaccounting.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    @Column(name = "name", nullable = false, length = 250)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "folder_id", nullable = false)
    private InventoryFolderEntity folder;

    @ManyToOne(optional = false)
    @JoinColumn(name = "unit_id", nullable = false)
    private UnitEntity unit;

}