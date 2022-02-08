package ru.home.inventoryaccounting.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentHeaderDto implements DtoInterface {
    private Long id;
    private BigDecimal amount;
    private String comment;
    private LocalDate date;
    private Boolean deleted;
    private String documentNumber;
    private Boolean registered;
    private PartnerDto partner;
    private UserDto user;
    private WarehouseDto warehouse;
    private WarehouseDto warehouseRecipient;
    private Integer typeDok;
    private Collection<DocumentBodyDto> documentBody;

}