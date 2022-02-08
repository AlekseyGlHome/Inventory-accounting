package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.response.DtoResponse;
import ru.home.inventoryaccounting.domain.dto.UserDto;
import ru.home.inventoryaccounting.domain.entity.UserEntity;
import ru.home.inventoryaccounting.domain.mapper.MapperUtiliti;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MapperUtiliti mapperUtiliti;

    /**
     * выбор пользователя по идентификатору
     *
     * @param id - идентификатор склада
     * @return UserDTO
     */
    public UserDto findById(long id) throws NotFoundException {
        Optional<UserEntity> user = userRepository.findById(id);
        return user.map(mapperUtiliti::mapToUserDto)
                .orElseThrow(() -> new NotFoundException("Пользователь с Id: " + id + " не найден."));
    }

    /**
     * выбор пользователя по вхождению в наименование
     *
     * @param offset - номер страницы
     * @param limit  - количество элементов на странице
     * @param query  - строка поиска
     * @return DTOResponse&lt;UserDTO&gt;
     */
    public DtoResponse<UserDto> findByQueryString(int offset, int limit,
                                                  String query) throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<UserEntity> users;
        if (!query.isEmpty() || !query.isBlank()) {
            users = userRepository.findByNameLike(pageRequest, query);
        } else {
            throw new InvalidRequestParameteException("Неверный параметр запроса");
        }

        return new DtoResponse<>(users.getTotalElements(), mapperUtiliti.mapToCollectionUserDto(users.getContent()));
    }

    /**
     * выбор всех пользователей
     *
     * @param offset - номер страницы
     * @param limit  - количество элементов на странице
     * @return DTOResponse&lt;UserDTO&gt;
     */
    public DtoResponse<UserDto> findAll(int offset, int limit) {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<UserEntity> users;
        users = userRepository.findAll(pageRequest);
        return new DtoResponse<>(users.getTotalElements(), mapperUtiliti.mapToCollectionUserDto(users.getContent()));
    }

    /**
     * выбор пользователя по наименованию
     *
     * @param offset - номер страницы
     * @param limit  - количество элементов на странице
     * @param name   - строка поиска
     * @return DTOResponse&lt;UserDTO&gt;
     */
    public UserDto findByName(int offset, int limit, String name) throws NotFoundException {
        Optional<UserEntity> user = userRepository.findByName(name);
        return user.map(mapperUtiliti::mapToUserDto)
                .orElseThrow(() -> new NotFoundException("Запись с Name: " + name + " не найдена."));
    }

    // создать страницу пагинации
    private PageRequest getPageRequest(int offset, int limit) {
        int numberPage = offset / limit;
        return PageRequest.of(numberPage, limit);
    }
}
