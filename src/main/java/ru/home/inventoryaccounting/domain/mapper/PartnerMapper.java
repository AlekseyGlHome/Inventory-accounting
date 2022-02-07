package ru.home.inventoryaccounting.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.home.inventoryaccounting.domain.DTO.PartnerDTO;
import ru.home.inventoryaccounting.domain.entity.PartnerEntity;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PartnerMapper implements MaperInterface<PartnerEntity, PartnerDTO> {

    /**
     * Преобразовать Partner в PartnerDTO
     *
     * @param partnerEntity - элемент Partner
     * @return PartnerDTO
     */
    @Override
    public PartnerDTO convertToDTO(PartnerEntity partnerEntity) {
        return PartnerDTO.builder()
                .id(partnerEntity.getId())
                .name(partnerEntity.getName())
                .deleted(partnerEntity.getDeleted())
                .build();
    }

    /**
     * Преобразовать коллекцию Partner в коллекцию PartnerDTO
     *
     * @param partnerEntities - колекция Partner
     * @return Collection&lt;PartnerDTO&gt;
     */
    @Override
    public Collection<PartnerDTO> convertCollectionToDTO(Collection<PartnerEntity> partnerEntities) {
        return partnerEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
