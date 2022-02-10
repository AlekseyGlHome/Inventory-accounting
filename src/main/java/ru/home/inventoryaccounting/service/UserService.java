package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.api.request.ParameterRequest;
import ru.home.inventoryaccounting.api.request.UserUpdateRequest;
import ru.home.inventoryaccounting.api.request.WarehouseUpdateRequest;
import ru.home.inventoryaccounting.api.response.DtoResponse;
import ru.home.inventoryaccounting.domain.dto.UserDto;
import ru.home.inventoryaccounting.domain.dto.WarehouseDto;
import ru.home.inventoryaccounting.domain.entity.UserEntity;
import ru.home.inventoryaccounting.domain.entity.WarehouseEntity;
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
    private final String MESSAGE_NOT_FOUND_NAME = "Запись с Name: %s не найдена.";

    /**
     * выбор пользователя по идентификатору
     */
    public UserDto findById(long id) throws NotFoundException {
        Optional<UserEntity> user = userRepository.findById(id);
        return user.map(mapperUtiliti::mapToUserDto)
                .orElseThrow(() -> new NotFoundException(String.format(MESSAGE_NOT_FOUND, id)));
    }

    /**
     * выбор пользователя по вхождению в наименование
     */
    public DtoResponse<UserDto> findByNameLike(ParameterRequest request) throws InvalidRequestParameteException {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);
        Page<UserEntity> users;
        if (!request.getQuery().isEmpty() || !request.getQuery().isBlank()) {
            users = userRepository.findByNameLike(pageRequest, request.getQuery());
        } else {
            throw new InvalidRequestParameteException(MESSAGE_BAD_REQUESR);
        }

        return new DtoResponse<>(true, "", users.getTotalElements(), mapperUtiliti.mapToCollectionUserDto(users.getContent()));
    }

    /**
     * выбор всех пользователей
     */
    public DtoResponse<UserDto> findAll(ParameterRequest request) {
        PageRequest pageRequest = PageRequestUtil.getPageToRequest(request);
        Page<UserEntity> users = userRepository.findAll(pageRequest);
        return new DtoResponse<>(true, "", users.getTotalElements(), mapperUtiliti.mapToCollectionUserDto(users.getContent()));
    }

    /**
     * общий запрос
     */
    public DtoResponse<UserDto> selectQuery(ParameterRequest request) throws InvalidRequestParameteException {
        if (!request.getQuery().isEmpty() || !request.getQuery().isBlank()) {
            return findByNameLike(request);
        }
        return findAll(request);
    }

    /**
     * выбор пользователя по наименованию
     */
    public UserDto findByName(ParameterRequest request) throws NotFoundException {
        Optional<UserEntity> user = userRepository.findByName(request.getQuery());
        return user.map(mapperUtiliti::mapToUserDto)
                .orElseThrow(() -> new NotFoundException(String.format(MESSAGE_NOT_FOUND_NAME, request.getQuery())));
    }


    // добавить карточку
    public UserDto add(UserUpdateRequest request) throws NotFoundException {
        UserEntity userEntity = fillInventory(new UserEntity(), request);
        return mapperUtiliti.mapToUserDto(userRepository.save(userEntity));
    }

    // обновить карточку
    public UserDto update(long id, UserUpdateRequest request) throws NotFoundException {
        UserEntity userEntity = fillInventory(mapperUtiliti.mapToUserEntity(findById(id)), request);
        return mapperUtiliti.mapToUserDto(userRepository.save(userEntity));
    }

    // заполнить карточку из запросса
    private UserEntity fillInventory(UserEntity userEntity, UserUpdateRequest request) {
        userEntity.setName(request.getName());
        userEntity.setIsDeleted(request.isDeleted());
        userEntity.setPassword(request.getPassword());
        return userEntity;
    }

    /**
     * удалить (переменную is_deleted в true)
     */
    public void deleteById(long id) throws NotFoundException {
        if (userRepository.updateIsDeleteToTrueById(id) <= 0) {
            throw new NotFoundException(String.format(MESSAGE_NOT_FOUND, id));
        }
    }

}
