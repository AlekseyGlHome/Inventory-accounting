package ru.home.inventoryaccounting.api.response;

import lombok.*;
import ru.home.inventoryaccounting.domain.DTO.PartnerDTO;
import ru.home.inventoryaccounting.domain.DTO.UnitDTO;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartnerResponse {
    private long numberOfRecord;
    private Collection<PartnerDTO> partners;
}
