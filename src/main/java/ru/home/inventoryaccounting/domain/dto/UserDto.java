package ru.home.inventoryaccounting.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.home.inventoryaccounting.domain.entity.UserEntity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements DtoInterface {
    private Long id;
    private Boolean isDeleted;
    private String name;
    private String password;

    public UserDto(UserEntity userEntity){
        setId(userEntity.getId());
        setIsDeleted(userEntity.getIsDeleted());
        setName(userEntity.getName());
        setPassword(userEntity.getPassword());
    }
}
