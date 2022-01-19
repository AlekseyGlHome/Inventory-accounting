package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.response.DTOResponse;
import ru.home.inventoryaccounting.domain.DTO.PartnerDTO;
import ru.home.inventoryaccounting.domain.entity.Partner;
import ru.home.inventoryaccounting.domain.mapper.PartnerMapper;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.PartnerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartnerService {

    private final PartnerMapper partnerMapper;
    private final PartnerRepository partnerRepository;

    public PartnerDTO findById(long id) throws NotFoundException {
        Optional<Partner> partner = partnerRepository.findById(id);
        return partner.map(partnerMapper::partnerToDTO)
                .orElseThrow(() -> new NotFoundException("Запись с Id: " + id + " не найдена."));
    }

    public DTOResponse<PartnerDTO> findByQueryString(int offset, int limit, String query) {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<Partner> partners;

        if (!query.isEmpty() || !query.isBlank()) {
            partners = partnerRepository.findByNameLike(pageRequest, query);
        } else {
            partners = partnerRepository.findAll(pageRequest);
        }

        return new DTOResponse<>(partners.getTotalElements(), getPartnerDTOS(partners));
    }

    private List<PartnerDTO> getPartnerDTOS(Page<Partner> units) {
        return units.getContent().stream()
                .map(partnerMapper::partnerToDTO)
                .collect(Collectors.toList());
    }

    private PageRequest getPageRequest(int offset, int limit) {
        int numberPage = offset / limit;

        return PageRequest.of(numberPage, limit);
    }

}
