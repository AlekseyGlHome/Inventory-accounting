package ru.home.inventoryaccounting.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentBodyDTO implements DTOInterface{
    private Long id;
    private BigDecimal amount;
    private Boolean deleted;
    private BigDecimal price;
    private BigDecimal quantity;
    private InventoryDTO inventory;
    private long receiptDocument;
    private long serialDocumentBody;
}
