package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.response.DTOResponse;
import ru.home.inventoryaccounting.domain.DTO.UserDTO;
import ru.home.inventoryaccounting.domain.entity.User;
import ru.home.inventoryaccounting.domain.mapper.UserMapper;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * выбор пользователя по идентификатору
     *
     * @param id - идентификатор склада
     * @return UserDTO
     * @throws NotFoundException
     */
    public UserDTO findById(long id) throws NotFoundException {
        Optional<User> user = userRepository.findById(id);
        return user.map(userMapper::userToDTO)
                .orElseThrow(() -> new NotFoundException("Запись с Id: " + id + " не найдена."));
    }

    /**
     * выбор пользователя по вхождению в наименование
     *
     * @param offset - номер страницы
     * @param limit  - количество элементов на странице
     * @param query  - строка поиска
     * @return DTOResponse&lt;UserDTO&gt;
     * @throws InvalidRequestParameteException
     */
    public DTOResponse<UserDTO> findByQueryString(int offset, int limit, String query) throws InvalidRequestParameteException {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<User> users;
        if (!query.isEmpty() || !query.isBlank()) {
            users = userRepository.findByNameLike(pageRequest, query);
        } else {
            throw new InvalidRequestParameteException("Неверный параметр запроса");
        }

        return new DTOResponse<>(users.getTotalElements(), getUserDTOS(users));
    }

    /**
     * выбор всех пользователей
     *
     * @param offset - номер страницы
     * @param limit  - количество элементов на странице
     * @return DTOResponse&lt;UserDTO&gt;
     */
    public DTOResponse<UserDTO> findAll(int offset, int limit) {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<User> users;
        users = userRepository.findAll(pageRequest);
        return new DTOResponse<>(users.getTotalElements(), getUserDTOS(users));
    }

    /**
     * выбор пользователя по наименованию
     *
     * @param offset - номер страницы
     * @param limit  - количество элементов на странице
     * @param name   - строка поиска
     * @return DTOResponse&lt;UserDTO&gt;
     * @throws NotFoundException
     */
    public UserDTO findByName(int offset, int limit, String name) throws NotFoundException {
        Optional<User> user = userRepository.findByName(name);
        return user.map(userMapper::userToDTO)
                .orElseThrow(() -> new NotFoundException("Запись с Name: " + name + " не найдена."));
    }

    // Преобразовать List<User> в List<UserDTO>
    private List<UserDTO> getUserDTOS(Page<User> users) {
        return users.getContent().stream()
                .map(userMapper::userToDTO)
                .collect(Collectors.toList());
    }

    // создать страницу пагинации
    private PageRequest getPageRequest(int offset, int limit) {
        int numberPage = offset / limit;
        return PageRequest.of(numberPage, limit);
    }
}
