package ru.home.inventoryaccounting.domain.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO implements DTOInterface{
    private Long id;
    private Boolean deleted;
    private String name;
    private String password;
}
