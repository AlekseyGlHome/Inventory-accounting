package ru.home.inventoryaccounting.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.home.inventoryaccounting.domain.entity.DocumentBody;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentHeaderDTO implements DTOInterface {
    private Long id;
    private BigDecimal amount;
    private String comment;
    private LocalDate date;
    private Boolean deleted;
    private String documentNumber;
    private Boolean registered;
    private PartnerDTO partner;
    private UserDTO user;
    private WarehouseDTO warehouse;
    private WarehouseDTO warehouseRecipient;
    private Integer typeDok;
    private Collection<DocumentBodyDTO> documentBody;

}
