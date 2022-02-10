package ru.home.inventoryaccounting.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto implements DtoInterface {
    private Long id;
    private Boolean isDeleted;
    private String name;
    private String password;
}
