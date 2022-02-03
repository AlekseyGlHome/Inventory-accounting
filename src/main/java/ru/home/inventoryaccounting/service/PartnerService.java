package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.response.DTOResponse;
import ru.home.inventoryaccounting.domain.DTO.PartnerDTO;
import ru.home.inventoryaccounting.domain.entity.Partner;
import ru.home.inventoryaccounting.domain.mapper.PartnerMapper;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.PartnerRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartnerService {

    private final PartnerMapper partnerMapper;
    private final PartnerRepository partnerRepository;

    /**
     * выбор партнера по идентификатору
     *
     * @param id - идентификатор
     * @return PartnerDTO
     * @throws NotFoundException
     */
    public PartnerDTO findById(long id) throws NotFoundException {
        Optional<Partner> partner = partnerRepository.findById(id);
        return partner.map(partnerMapper::convertToDTO)
                .orElseThrow(() -> new NotFoundException("Партнер с Id: " + id + " не найден."));
    }


    /**
     * выбор партнеров по входждению в наименование
     *
     * @param offset - номер страницы
     * @param limit  - количество элементов на странице
     * @param query  - строка поиска
     * @return DTOResponse&lt;UnitDTO&gt;
     * @throws InvalidRequestParameteException
     */
    public DTOResponse<PartnerDTO> findByQueryString(int offset, int limit,
                                                     String query) throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<Partner> partners;

        if (!query.isEmpty() || !query.isBlank()) {
            partners = partnerRepository.findByNameLike(pageRequest, query);
        } else {
            throw new InvalidRequestParameteException("Неверный параметр запроса");
        }

        return new DTOResponse<>(partners.getTotalElements(),
                partnerMapper.convertCollectionToDTO(partners.getContent()));
    }

    /**
     * выбор всех партнеров
     *
     * @param offset - номер страницы
     * @param limit  - количество элементов на странице
     * @return DTOResponse&lt;PartnerDTO&gt;
     */
    public DTOResponse<PartnerDTO> findAll(int offset, int limit, String query) {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<Partner> partners;
        partners = partnerRepository.findAll(pageRequest);
        return new DTOResponse<>(partners.getTotalElements(),
                partnerMapper.convertCollectionToDTO(partners.getContent()));
    }



    // создать страницу пагинации
    private PageRequest getPageRequest(int offset, int limit) {
        int numberPage = offset / limit;

        return PageRequest.of(numberPage, limit);
    }

}
