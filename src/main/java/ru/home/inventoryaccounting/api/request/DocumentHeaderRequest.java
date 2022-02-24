package ru.home.inventoryaccounting.api.request;

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
public class DocumentHeaderRequest {
    private Long id;
    private BigDecimal amount;
    private String comment;
    private LocalDate date;
    private Boolean isDeleted;
    private String documentNumber;
    private Boolean isRegistered;
    private Long partnerId;
    private Long userId;
    private Long warehouseId;
    private Long warehouseRecipientId;
    private Integer typeDok;

}
