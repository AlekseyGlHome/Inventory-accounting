package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.response.DTOResponse;
import ru.home.inventoryaccounting.domain.DTO.WarehouseDTO;
import ru.home.inventoryaccounting.domain.entity.Warehouse;
import ru.home.inventoryaccounting.domain.mapper.WarehouseMapper;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.WarehouseRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseMapper warehouseMapper;
    private final WarehouseRepository warehouseRepository;

    public WarehouseDTO findById(long id) throws NotFoundException {
        Optional<Warehouse> warehouse = warehouseRepository.findById(id);
        return warehouse.map(warehouseMapper::warehouseToDTO).orElseThrow(() -> new NotFoundException("Запись с Id: " + id + " не найдена."));
    }

    public DTOResponse<WarehouseDTO> findByQueryString(int offset, int limit, String query) {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<Warehouse> warehouses;
        if (!query.isEmpty() || !query.isBlank()) {
            warehouses = warehouseRepository.findByNameLike(pageRequest, query);
        } else {
            warehouses = warehouseRepository.findByDeletedFalse(pageRequest);
        }
        return new DTOResponse<>(warehouses.getTotalElements(), getWarehouseDTOS(warehouses));
    }

    private List<WarehouseDTO> getWarehouseDTOS(Page<Warehouse> warehouse) {
        return warehouse.getContent().stream()
                .map(warehouseMapper::warehouseToDTO)
                .collect(Collectors.toList());
    }

    private PageRequest getPageRequest(int offset, int limit) {
        int numberPage = offset / limit;

        return PageRequest.of(numberPage, limit);
    }
}
