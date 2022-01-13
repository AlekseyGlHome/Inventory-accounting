package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.response.InventoryResponse;
import ru.home.inventoryaccounting.domain.DTO.InventoryDTO;
import ru.home.inventoryaccounting.domain.entity.Inventory;
import ru.home.inventoryaccounting.repository.InventoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryFolderService inventoryFolderService;
    private final UnitService unitService;

    public InventoryResponse findAll(int offset, int limit) {

        PageRequest pageRequest = getPageRequest(offset,limit);
        Page<Inventory> inventories = inventoryRepository.findAll(pageRequest);
        List<InventoryDTO> inventoryDTOList = inventories.getContent().stream()
                .map(this::buildInventoryDTO)
                .collect(Collectors.toList());

        return new InventoryResponse(inventories.getTotalElements(), inventoryDTOList);
    }

    public InventoryDTO findById(long id){
        Optional<Inventory> inventory = inventoryRepository.findById(id);

        return inventory.map(this::buildInventoryDTO).orElse(null);

    }

    public InventoryDTO buildInventoryDTO(Inventory inventory) {
        return InventoryDTO.builder()
                .id(inventory.getId())
                .name(inventory.getName())
                .deleted(inventory.getDeleted())
                .folder(inventoryFolderService.buildInventoryFolderDTO(inventory.getFolder()))
                .unit(unitService.buildUnitDTO(inventory.getUnit()))
                .build();
    }

    private PageRequest getPageRequest(int offset, int limit) {
        int numberPage = offset / limit;
        return PageRequest.of(numberPage, limit);
    }
}
