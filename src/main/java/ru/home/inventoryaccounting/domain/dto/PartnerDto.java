package ru.home.inventoryaccounting.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.home.inventoryaccounting.domain.entity.PartnerEntity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartnerDto implements DtoInterface {
    private Long id;
    private Boolean isDeleted;
    private String name;

    public PartnerDto(PartnerEntity partnerEntity) {
        setId(partnerEntity.getId());
        setIsDeleted(partnerEntity.getIsDeleted());
        setName(partnerEntity.getName());
    }
}
