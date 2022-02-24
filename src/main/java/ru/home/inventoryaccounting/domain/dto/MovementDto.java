package ru.home.inventoryaccounting.domain.dto;

import lombok.*;
import ru.home.inventoryaccounting.domain.entity.DocumentBodyEntity;
import ru.home.inventoryaccounting.domain.entity.DocumentHeaderEntity;
import ru.home.inventoryaccounting.domain.entity.InventoryEntity;
import ru.home.inventoryaccounting.domain.entity.WarehouseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovementDto implements DtoInterface{
    private Long id;
    private BigDecimal amount;
    private LocalDate date;
    private BigDecimal price;
    private BigDecimal quantity;
    private DocumentHeaderDto documentHeader;
    private InventoryDto inventory;
    private DocumentHeaderDto receiptDocument;
    private DocumentBodyDto serialDocuBody;
    private WarehouseDto warehouse;
    private DocumentBodyDto serialDocumentBody;
}