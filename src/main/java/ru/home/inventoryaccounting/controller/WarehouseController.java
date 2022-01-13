package ru.home.inventoryaccounting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.home.inventoryaccounting.api.response.InventoryResponse;
import ru.home.inventoryaccounting.domain.DTO.InventoryDTO;
import ru.home.inventoryaccounting.service.InventoryService;

@RestController
@RequiredArgsConstructor
public class WarehouseController {

    private final InventoryService inventoryService;

    @GetMapping("/inventory")
    public ResponseEntity<InventoryResponse> getInventory(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {

        InventoryResponse inventoryResponse = inventoryService.findAll(offset, limit);
        return ResponseEntity.ok(inventoryResponse);

    }

    @GetMapping("/inventory/{id}")
    public ResponseEntity getInventoryById(@PathVariable long id){

        InventoryDTO inventoryDTO = inventoryService.findById(id);
        if (inventoryDTO!=null){
            return ResponseEntity.ok(inventoryDTO);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Records with Id: "+id+" not found");
        }


    }
}
