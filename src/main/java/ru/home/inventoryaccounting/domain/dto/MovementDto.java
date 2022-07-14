package ru.home.inventoryaccounting.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovementDto implements DtoInterface {
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