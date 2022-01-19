package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.response.DTOResponse;
import ru.home.inventoryaccounting.domain.DTO.InventoryFolderDTO;
import ru.home.inventoryaccounting.domain.entity.InventoryFolder;
import ru.home.inventoryaccounting.domain.mapper.InventoryFolderMapper;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.InventoryFolderRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryFolderService {


    private final InventoryFolderMapper inventoryFolderMapper;
    private final InventoryFolderRepository inventoryFolderRepository;

    public InventoryFolderDTO findById(long id) throws NotFoundException {
        Optional<InventoryFolder> inventoryFolder = inventoryFolderRepository.findById(id);
        return inventoryFolder.map(inventoryFolderMapper::inventoryFolderToDTO)
                .orElseThrow(() -> new NotFoundException("Запись с Id: " + id + " не найдена."));
    }

    public DTOResponse<InventoryFolderDTO> findByQueryString(int offset, int limit, String query) {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<InventoryFolder> inventoryFolders;
        if (!query.isEmpty() || !query.isBlank()) {
            inventoryFolders = inventoryFolderRepository.findByNameLike(pageRequest, query);
        } else {
            inventoryFolders = inventoryFolderRepository.findAll(pageRequest);
        }

        return new DTOResponse<>(inventoryFolders.getTotalElements(), getInventoryFolderDTOS(inventoryFolders));
    }


    private List<InventoryFolderDTO> getInventoryFolderDTOS(Page<InventoryFolder> inventoriInventoryFolders) {
        return inventoriInventoryFolders.getContent().stream()
                .map(inventoryFolderMapper::inventoryFolderToDTO)
                .collect(Collectors.toList());
    }

    private PageRequest getPageRequest(int offset, int limit) {
        int numberPage = offset / limit;

        return PageRequest.of(numberPage, limit);
    }
}
