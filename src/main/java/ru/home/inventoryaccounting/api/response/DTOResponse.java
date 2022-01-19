package ru.home.inventoryaccounting.api.response;

import lombok.*;
import ru.home.inventoryaccounting.domain.DTO.DTOInterface;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DTOResponse <T extends DTOInterface>{
    private long numberOfRecord;
    private Collection<T> data;
}
