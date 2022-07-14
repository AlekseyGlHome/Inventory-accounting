package ru.home.inventoryaccounting.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.home.inventoryaccounting.domain.enums.TypeDok;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentHeaderRequest {
    private Long id;
    private BigDecimal amount;
    private String comment;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate date;
    private Boolean isDeleted;
    private String documentNumber;
    private Boolean isRegistered;
    private Long partnerId;
    private Long userId;
    private Long warehouseId;
    private Long warehouseRecipientId;
    private TypeDok typeDok;
    private Collection<DocumentBodyRequest> bodyDto;
}
