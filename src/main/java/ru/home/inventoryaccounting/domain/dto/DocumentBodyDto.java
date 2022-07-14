package ru.home.inventoryaccounting.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.home.inventoryaccounting.domain.entity.DocumentBodyEntity;

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
//    private DocumentHeaderDto documentHeader;
//    private MovementDto receiptDocument;
    //private DocumentBodyDto serialDocumentBody;


    public DocumentBodyDto(DocumentBodyEntity documentBodyEntity) {
        setId(documentBodyEntity.getId());
        setAmount(documentBodyEntity.getAmount());
        setIsDeleted(documentBodyEntity.getIsDeleted());
        setPrice(documentBodyEntity.getPrice());
        setQuantity(documentBodyEntity.getQuantity());
        setInventory(new InventoryDto(documentBodyEntity.getInventory()));
    }

}
