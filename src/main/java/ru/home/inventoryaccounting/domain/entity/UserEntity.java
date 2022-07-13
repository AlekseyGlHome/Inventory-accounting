package ru.home.inventoryaccounting.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.home.inventoryaccounting.domain.dto.UserDto;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "name", nullable = false, length = 250)
    private String name;

    @Column(name = "password", length = 250)
    private String password;

    public UserEntity(UserDto userDto){
        setId(userDto.getId());
        setIsDeleted(userDto.getIsDeleted());
        setName(userDto.getName());
        setPassword(userDto.getPassword());
    }

}