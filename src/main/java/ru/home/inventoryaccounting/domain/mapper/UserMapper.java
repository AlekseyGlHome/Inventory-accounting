package ru.home.inventoryaccounting.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.home.inventoryaccounting.domain.DTO.UserDTO;
import ru.home.inventoryaccounting.domain.entity.User;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public UserDTO userToDTO(User user){
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .deleted(user.getDeleted())
                .build();
    }
}
