package ru.home.inventoryaccounting.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.home.inventoryaccounting.domain.DTO.UserDTO;
import ru.home.inventoryaccounting.domain.entity.UserEntity;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper implements MaperInterface<UserEntity, UserDTO> {


    /**
     * Преобразовать User в UserDTO
     *
     * @param userEntity - элемент User
     * @return UserDTO
     */
    @Override
    public UserDTO convertToDTO(UserEntity userEntity) {
        return UserDTO.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .deleted(userEntity.getDeleted())
                .build();
    }

    /**
     * Преобразовать коллекцию User в коллекцию UserDTO
     *
     * @param userEntities - колекция User
     * @return Collection&lt;UserDTO&gt;
     */
    @Override
    public Collection<UserDTO> convertCollectionToDTO(Collection<UserEntity> userEntities) {
        return userEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
