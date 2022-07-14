package ru.home.inventoryaccounting.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.home.inventoryaccounting.domain.dto.WarehouseDto;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "warehouse")
public class WarehouseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "company", length = 150)
    private String company;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "name", nullable = false, length = 250)
    private String name;

    @Column(name = "person", length = 200)
    private String person;

    public WarehouseEntity(WarehouseDto warehouseDto) {
        setId(warehouseDto.getId());
        setCompany(warehouseDto.getCompany());
        setIsDeleted(warehouseDto.getIsDeleted());
        setName(warehouseDto.getName());
        setPerson(warehouseDto.getPerson());
    }

}