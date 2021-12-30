package ru.home.inventoryaccounting.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "inventory_folder")
public class InventoryFolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    @Column(name = "name", nullable = false, length = 250)
    private String name;

    @OneToMany(mappedBy = "folder",fetch = FetchType.LAZY)
    private Collection<Inventory> inventories = new ArrayList<>();

}