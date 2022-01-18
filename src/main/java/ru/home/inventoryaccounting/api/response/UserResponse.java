package ru.home.inventoryaccounting.api.response;

import lombok.*;
import ru.home.inventoryaccounting.domain.DTO.UserDTO;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private long numberOfRecord;
    private Collection<UserDTO> users;
}
