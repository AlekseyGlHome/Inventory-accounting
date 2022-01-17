package ru.home.inventoryaccounting.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.home.inventoryaccounting.domain.DTO.PartnerDTO;
import ru.home.inventoryaccounting.domain.entity.Partner;

@Component
@RequiredArgsConstructor
public class PartnerMapper {

    public PartnerDTO partnerToDTO(Partner partner){
        return PartnerDTO.builder()
                .id(partner.getId())
                .name(partner.getName())
                .deleted(partner.getDeleted())
                .build();
    }
}
