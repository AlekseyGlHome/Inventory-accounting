package ru.home.inventoryaccounting.api.request;

import ru.home.inventoryaccounting.domain.dto.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
