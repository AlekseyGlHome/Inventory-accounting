package ru.home.inventoryaccounting.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.home.inventoryaccounting.domain.entity.MovementEntity;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentBodyDto implements DtoInterface {
    private Long id;
    private BigDecimal amount;
    private Boolean isDeleted;
    private BigDecimal price;
    private BigDecimal quantity;
    private InventoryDto inventory;
    private DocumentHeaderDto documentHeader;
    private MovementDto receiptDocument;
    //private DocumentBodyDto serialDocumentBody;


}
