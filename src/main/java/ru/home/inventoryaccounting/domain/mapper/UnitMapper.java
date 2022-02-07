package ru.home.inventoryaccounting.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.home.inventoryaccounting.domain.DTO.UnitDTO;
import ru.home.inventoryaccounting.domain.entity.UnitEntity;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UnitMapper {

    /**
     * Из Entity в DTO
     */
    public UnitDTO mapToUnitDto(UnitEntity unitEntity) {
        return UnitDTO.builder()
                .id(unitEntity.getId())
                .deleted(unitEntity.getDeleted())
                .name(unitEntity.getName())
                .build();
    }

    /**
     * Из DTO в Entity
     */
    public UnitEntity mapToUnit(UnitDTO unitDTO){
        UnitEntity unitEntity = new UnitEntity();
        unitEntity.setId(unitDTO.getId());
        unitEntity.setName(unitDTO.getName());
        unitEntity.setDeleted(unitDTO.getDeleted());
        return unitEntity;
    }

    /**
     * Преобразовать коллекцию Entity в DTO
     */
    public Collection<UnitDTO> convertCollectionToDTO(Collection<UnitEntity> unitEntities) {
        return unitEntities.stream()
                .map(this::mapToUnitDto)
                .collect(Collectors.toList());
    }
}
