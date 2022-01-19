package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.response.DTOResponse;
import ru.home.inventoryaccounting.domain.DTO.InventoryDTO;
import ru.home.inventoryaccounting.domain.entity.Inventory;
import ru.home.inventoryaccounting.domain.mapper.InventoryMapper;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.InventoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryMapper inventoryMapper;
    private final InventoryRepository inventoryRepository;

    public InventoryDTO findById(long id) throws NotFoundException {
        Optional<Inventory> inventory = inventoryRepository.findById(id);
        return inventory.map(inventoryMapper::inventoryToDTO)
                .orElseThrow(() -> new NotFoundException("Запись с Id: " + id + " не найдена."));
    }

    public DTOResponse<InventoryDTO> findByQueryString(int offset, int limit, String query, long folderId) {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<Inventory> inventories;
        if ((!query.isEmpty() || !query.isBlank()) && (folderId == 0)) {
            inventories = inventoryRepository.findByNameLike(pageRequest, query);
        } else if ((query.isEmpty() || query.isBlank()) && (folderId > 0)) {
            inventories = inventoryRepository.findByFolder_IdEquals(pageRequest, folderId);
        } else if ((!query.isEmpty() || !query.isBlank()) && (folderId > 0)) {
            inventories = inventoryRepository.findByNameLikeAndFolder_IdEquals(pageRequest, query, folderId);
        } else {
            inventories = inventoryRepository.findAll(pageRequest);
        }

        return new DTOResponse<>(inventories.getTotalElements(), getInventoryDTOS(inventories));
    }


    private List<InventoryDTO> getInventoryDTOS(Page<Inventory> inventories) {
        return inventories.getContent().stream()
                .map(inventoryMapper::inventoryToDTO)
                .collect(Collectors.toList());
    }

    private PageRequest getPageRequest(int offset, int limit) {
        int numberPage = offset / limit;

        return PageRequest.of(numberPage, limit);
    }
}
