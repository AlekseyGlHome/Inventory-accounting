package ru.home.inventoryaccounting.api.response;

import lombok.*;
import ru.home.inventoryaccounting.domain.dto.DtoInterface;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DtoResponse<T extends DtoInterface>{
    private long numberOfRecord;
    private Collection<T> data;
}
