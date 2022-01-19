package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.response.DTOResponse;
import ru.home.inventoryaccounting.domain.DTO.UnitDTO;
import ru.home.inventoryaccounting.domain.entity.Unit;
import ru.home.inventoryaccounting.domain.mapper.UnitMapper;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.UnitRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UnitService {

    UnitMapper unitMapper;
    UnitRepository unitRepository;

    public UnitDTO findById(long id) throws NotFoundException {
        Optional<Unit> unit = unitRepository.findById(id);
        return unit.map(unitMapper::unitToDTO)
                .orElseThrow(() -> new NotFoundException("Запись с Id: " + id + " не найдена."));
    }

    public DTOResponse<UnitDTO> findByQueryString(int offset, int limit, String query) {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<Unit> units;

        if (!query.isEmpty() || !query.isBlank()) {
            units = unitRepository.findByNameLike(pageRequest, query);
        } else {
            units = unitRepository.findAll(pageRequest);
        }

        return new DTOResponse<>(units.getTotalElements(), getUnitDTOS(units));
    }

    private List<UnitDTO> getUnitDTOS(Page<Unit> units) {
        return units.getContent().stream()
                .map(unitMapper::unitToDTO)
                .collect(Collectors.toList());
    }

    private PageRequest getPageRequest(int offset, int limit) {
        int numberPage = offset / limit;

        return PageRequest.of(numberPage, limit);
    }

}
