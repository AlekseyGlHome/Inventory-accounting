package ru.home.inventoryaccounting.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.home.inventoryaccounting.domain.DTO.UnitDTO;
import ru.home.inventoryaccounting.domain.entity.Unit;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UnitMapper implements MaperInterface<Unit, UnitDTO> {

    /**
     * Преобразовать Unit в UnitDTO
     *
     * @param unit - элемент Unit
     * @return UnitDTO
     */
    @Override
    public UnitDTO convertToDTO(Unit unit) {
        return UnitDTO.builder()
                .id(unit.getId())
                .deleted(unit.getDeleted())
                .name(unit.getName())
                .build();
    }

    /**
     * Преобразовать коллекцию Unit в коллекцию UnitDTO
     *
     * @param units - колекция Unit
     * @return Collection&lt;DocumentBodyDTO&gt;
     */
    @Override
    public Collection<UnitDTO> convertCollectionToDTO(Collection<Unit> units) {
        return units.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
