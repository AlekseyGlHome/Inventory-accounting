package ru.home.inventoryaccounting.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.home.inventoryaccounting.domain.DTO.UnitDTO;
import ru.home.inventoryaccounting.domain.entity.Unit;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UnitMapper {

    /**
     * Из Entity в DTO
     */
    public UnitDTO mapToUnitDto(Unit unit) {
        return UnitDTO.builder()
                .id(unit.getId())
                .deleted(unit.getDeleted())
                .name(unit.getName())
                .build();
    }

    /**
     * Из DTO в Entity
     */
    public Unit mapToUnit(UnitDTO unitDTO){
        Unit unit = new Unit();
        unit.setId(unitDTO.getId());
        unit.setName(unitDTO.getName());
        unit.setDeleted(unitDTO.getDeleted());
        return unit;
    }

    /**
     * Преобразовать коллекцию Entity в DTO
     */
    public Collection<UnitDTO> convertCollectionToDTO(Collection<Unit> units) {
        return units.stream()
                .map(this::mapToUnitDto)
                .collect(Collectors.toList());
    }
}
