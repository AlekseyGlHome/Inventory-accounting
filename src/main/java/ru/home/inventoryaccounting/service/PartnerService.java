package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.response.DtoResponse;
import ru.home.inventoryaccounting.domain.dto.PartnerDto;
import ru.home.inventoryaccounting.domain.entity.PartnerEntity;
import ru.home.inventoryaccounting.domain.mapper.MapperUtiliti;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.PartnerRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartnerService {

    private final MapperUtiliti mapperUtiliti;
    private final PartnerRepository partnerRepository;

    /**
     * выбор партнера по идентификатору
     *
     * @param id - идентификатор
     * @return PartnerDTO
     */
    public PartnerDto findById(long id) throws NotFoundException {
        Optional<PartnerEntity> partner = partnerRepository.findById(id);
        return partner.map(mapperUtiliti::mapToPartnerDto)
                .orElseThrow(() -> new NotFoundException("Партнер с Id: " + id + " не найден."));
    }


    /**
     * выбор партнеров по входждению в наименование
     *
     * @param offset - номер страницы
     * @param limit  - количество элементов на странице
     * @param query  - строка поиска
     * @return DTOResponse&lt;UnitDTO&gt;
     */
    public DtoResponse<PartnerDto> findByQueryString(int offset, int limit,
                                                     String query) throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<PartnerEntity> partners;

        if (!query.isEmpty() || !query.isBlank()) {
            partners = partnerRepository.findByNameLike(pageRequest, query);
        } else {
            throw new InvalidRequestParameteException("Неверный параметр запроса");
        }

        return new DtoResponse<>(true, "", partners.getTotalElements(),
                mapperUtiliti.mapToCollectionPartnerDto(partners.getContent()));
    }

    /**
     * выбор всех партнеров
     *
     * @param offset - номер страницы
     * @param limit  - количество элементов на странице
     * @return DTOResponse&lt;PartnerDTO&gt;
     */
    public DtoResponse<PartnerDto> findAll(int offset, int limit, String query) {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<PartnerEntity> partners;
        partners = partnerRepository.findAll(pageRequest);
        return new DtoResponse<>(true, "", partners.getTotalElements(),
                mapperUtiliti.mapToCollectionPartnerDto(partners.getContent()));
    }



    // создать страницу пагинации
    private PageRequest getPageRequest(int offset, int limit) {
        int numberPage = offset / limit;

        return PageRequest.of(numberPage, limit);
    }

}
