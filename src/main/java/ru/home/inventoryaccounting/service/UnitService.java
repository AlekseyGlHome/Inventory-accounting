package ru.home.inventoryaccounting.service;

import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.domain.DTO.UnitDTO;
import ru.home.inventoryaccounting.domain.entity.Unit;

@Service
public class UnitService {

    public UnitDTO buildUnitDTO(Unit unit) {
        return UnitDTO.builder().id(unit.getId())
                .deleted(unit.getDeleted())
                .name(unit.getName())
                .build();
    }
}
