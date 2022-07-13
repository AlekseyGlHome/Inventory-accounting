package ru.home.inventoryaccounting.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.home.inventoryaccounting.domain.entity.DocumentBodyEntity;
import ru.home.inventoryaccounting.domain.entity.DocumentHeaderEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentHeaderDto implements DtoInterface {
    private Long id;
    private BigDecimal amount;
    private String comment;
    private LocalDate date;
    private Boolean isDeleted;
    private String documentNumber;
    private Boolean isRegistered;
    private PartnerDto partner;
    private UserDto user;
    private WarehouseDto warehouse;
    private WarehouseDto warehouseRecipient;
    private Integer typeDok;
    //private Collection<DocumentBodyDto> documentBody;

    public DocumentHeaderDto(DocumentHeaderEntity documentHeaderEntity){
        setId(documentHeaderEntity.getId());
        setAmount(documentHeaderEntity.getAmount());
        setComment(documentHeaderEntity.getComment());
        setDate(documentHeaderEntity.getDate());
        setIsDeleted(documentHeaderEntity.getIsDeleted());
        setDocumentNumber(documentHeaderEntity.getDocumentNumber());
        setIsRegistered(documentHeaderEntity.getIsRegistered());
        setPartner(new PartnerDto(documentHeaderEntity.getPartner()));
        setUser(new UserDto(documentHeaderEntity.getUser()));
        setWarehouse(new WarehouseDto(documentHeaderEntity.getWarehouse()));
        setTypeDok(documentHeaderEntity.getTypeDok());
        if (documentHeaderEntity.getWarehouseRecipient()!=null){
            setWarehouseRecipient(new WarehouseDto(documentHeaderEntity.getWarehouseRecipient()));
        }
    }

}
