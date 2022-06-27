package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.request.RequestParametersForDirectories;
import ru.home.inventoryaccounting.api.request.UserRequest;
import ru.home.inventoryaccounting.api.response.DtoResponse;
import ru.home.inventoryaccounting.domain.dto.UserDto;
import ru.home.inventoryaccounting.domain.entity.UserEntity;
import ru.home.inventoryaccounting.domain.mapper.MapperUtiliti;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.repository.UserRepository;
import ru.home.inventoryaccounting.util.PageRequestUtil;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MapperUtiliti mapperUtiliti;

    private final String MESSAGE_NOT_FOUND = "Пользователь с Id: %s не найдена.";
    private final String MESSAGE_BAD_REQUESR = "Неверный параметр запроса";
    private final String MESSAGE_NOT_FOUND_NAME = "Пользователь Name: %s не найдена.";

    /**
     * выбор пользователя по идентификатору
     */
    public UserDto findById(long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        return user.map(mapperUtiliti::mapToUserDto)
                .orElseThrow(() -> new NotFoundException(String.format(MESSAGE_NOT_FOUND, id)));
    }


    /**
     * выбор пользователя по вхождению в наименование
     */
    public DtoResponse<UserDto> findByNameLike(RequestParametersForDirectories request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);
        Page<UserEntity> users;
        if (!request.getQuery().isEmpty() || !request.getQuery().isBlank()) {
            users = userRepository.findByNameLike(pageRequest, request.getQuery());
        } else {
            throw new InvalidRequestParameteException(MESSAGE_BAD_REQUESR);
        }

        return new DtoResponse<>(users.getTotalElements(), mapperUtiliti.mapToCollectionUserDto(users.getContent()));
    }

    /**
     * выбор всех пользователей
     */
    public DtoResponse<UserDto> findAll(RequestParametersForDirectories request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);
        Page<UserEntity> users = userRepository.findAll(pageRequest);
        return new DtoResponse<>(users.getTotalElements(), mapperUtiliti.mapToCollectionUserDto(users.getContent()));
    }

    /**
     * общий запрос
     */
    public DtoResponse<UserDto> selectQuery(RequestParametersForDirectories request) {
        if (!request.getQuery().isEmpty() || !request.getQuery().isBlank()) {
            return findByNameLike(request);
        }
        return findAll(request);
    }

    /**
     * выбор пользователя по наименованию
     */
    public UserDto findByName(RequestParametersForDirectories request) {
        Optional<UserEntity> user = userRepository.findByName(request.getQuery());
        return user.map(mapperUtiliti::mapToUserDto)
                .orElseThrow(() -> new NotFoundException(String.format(MESSAGE_NOT_FOUND_NAME, request.getQuery())));
    }


    // добавить карточку
    public UserDto add(UserRequest request) {
        UserEntity userEntity = fillInventory(new UserEntity(), request);
        return mapperUtiliti.mapToUserDto(userRepository.save(userEntity));
    }

    // обновить карточку
    public UserDto update(long id, UserRequest request) {
        UserEntity userEntity = fillInventory(mapperUtiliti.mapToUserEntity(findById(id)), request);
        return mapperUtiliti.mapToUserDto(userRepository.save(userEntity));
    }

    // заполнить карточку из запросса
    private UserEntity fillInventory(UserEntity userEntity, UserRequest request) {
        userEntity.setName(request.getName());
        userEntity.setIsDeleted(request.isDeleted());
        userEntity.setPassword(request.getPassword());
        return userEntity;
    }

    /**
     * удалить (переменную is_deleted в true)
     */
    public void deleteById(long id) {
        if (userRepository.updateIsDeleteToTrueById(id) <= 0) {
            throw new NotFoundException(String.format(MESSAGE_NOT_FOUND, id));
        }
    }

}
