package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.response.UserResponse;
import ru.home.inventoryaccounting.domain.DTO.UserDTO;
import ru.home.inventoryaccounting.domain.entity.User;
import ru.home.inventoryaccounting.domain.mapper.UserMapper;
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

    public UserDTO findById(long id) throws NotFoundException {
        Optional<User> user = userRepository.findById(id);
        return user.map(userMapper::userToDTO)
                .orElseThrow(() -> new NotFoundException("Запись с Id: " + id + " не найдена."));
    }

    public UserResponse findByQueryString(int offset, int limit, String query) {
        PageRequest pageRequest = getPageRequest(offset, limit);
        Page<User> users;
        if (!query.isEmpty() || !query.isBlank()) {
            users = userRepository.findByNameLike(pageRequest, query);
        } else {
            users = userRepository.findAll(pageRequest);
        }

        return new UserResponse(users.getTotalElements(), getUserDTOS(users));
    }

    public UserDTO findByName(int offset, int limit, String name) throws NotFoundException {
        Optional<User> user = userRepository.findByName(name);
        return user.map(userMapper::userToDTO)
                .orElseThrow(() -> new NotFoundException("Запись с Name: " + name + " не найдена."));
    }

    private List<UserDTO> getUserDTOS(Page<User> users) {
        return users.getContent().stream()
                .map(userMapper::userToDTO)
                .collect(Collectors.toList());
    }

    private PageRequest getPageRequest(int offset, int limit) {
        int numberPage = offset / limit;

        return PageRequest.of(numberPage, limit);
    }
}
