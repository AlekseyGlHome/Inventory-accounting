package ru.home.inventoryaccounting.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import ru.home.inventoryaccounting.domain.dto.DtoInterface;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoResponse<T extends DtoInterface>{
    private Boolean result;
    private String message;
    private Long numberOfRecord;
    private Collection<T> data;
}
