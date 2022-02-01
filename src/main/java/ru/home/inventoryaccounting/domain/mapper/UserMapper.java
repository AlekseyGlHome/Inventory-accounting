package ru.home.inventoryaccounting.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.home.inventoryaccounting.domain.DTO.UserDTO;
import ru.home.inventoryaccounting.domain.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper implements MaperInterface<User, UserDTO> {


    /**
     * Преобразовать User в UserDTO
     *
     * @param user - элемент User
     * @return UserDTO
     */
    @Override
    public UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .deleted(user.getDeleted())
                .build();
    }

    /**
     * Преобразовать коллекцию User в коллекцию UserDTO
     *
     * @param users - колекция User
     * @return Collection&lt;UserDTO&gt;
     */
    @Override
    public Collection<UserDTO> convertCollectionToDTO(Collection<User> users) {
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
