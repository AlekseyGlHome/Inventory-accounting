package ru.home.inventoryaccounting.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.home.inventoryaccounting.domain.DTO.PartnerDTO;
import ru.home.inventoryaccounting.domain.entity.Partner;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PartnerMapper implements MaperInterface<Partner, PartnerDTO> {

    /**
     * Преобразовать Partner в PartnerDTO
     *
     * @param partner - элемент Partner
     * @return PartnerDTO
     */
    @Override
    public PartnerDTO convertToDTO(Partner partner) {
        return PartnerDTO.builder()
                .id(partner.getId())
                .name(partner.getName())
                .deleted(partner.getDeleted())
                .build();
    }

    /**
     * Преобразовать коллекцию Partner в коллекцию PartnerDTO
     *
     * @param partners - колекция Partner
     * @return Collection&lt;PartnerDTO&gt;
     */
    @Override
    public Collection<PartnerDTO> convertCollectionToDTO(Collection<Partner> partners) {
        return partners.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
