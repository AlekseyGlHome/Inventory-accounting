package ru.home.inventoryaccounting.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.home.inventoryaccounting.domain.DTO.UnitDTO;
import ru.home.inventoryaccounting.domain.entity.Unit;

@Component
@RequiredArgsConstructor
public class UnitMapper {

    public UnitDTO unitToDTO(Unit unit) {
        return UnitDTO.builder().id(unit.getId())
                .deleted(unit.getDeleted())
                .name(unit.getName())
                .build();
    }
}
