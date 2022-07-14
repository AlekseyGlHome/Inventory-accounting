package ru.home.inventoryaccounting.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.home.inventoryaccounting.domain.dto.DtoInterface;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentBodyRequest implements DtoInterface {
    private Long id;
    private BigDecimal amount;
    private Boolean isDeleted;
    private BigDecimal price;
    private BigDecimal quantity;
    private Long inventoryId;

}
