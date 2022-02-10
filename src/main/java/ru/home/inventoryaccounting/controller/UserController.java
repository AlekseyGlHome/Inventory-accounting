package ru.home.inventoryaccounting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.home.inventoryaccounting.api.request.ParameterRequest;
import ru.home.inventoryaccounting.api.request.UserUpdateRequest;
import ru.home.inventoryaccounting.api.response.DtoResponse;
import ru.home.inventoryaccounting.domain.dto.UserDto;
import ru.home.inventoryaccounting.exception.InvalidRequestParameteException;
import ru.home.inventoryaccounting.exception.NotFoundException;
import ru.home.inventoryaccounting.service.UserService;
import ru.home.inventoryaccounting.util.RequestParameterUtil;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<DtoResponse<UserDto>> getByAllOrFilter(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "query", defaultValue = "") String query,
            @RequestParam(name = "sortColumns", defaultValue = "name") String sortColumns,
            @RequestParam(name = "sortingDirection", defaultValue = "ASC") String sortingDirection) {

        DtoResponse<UserDto> inventoryResponse;
        ParameterRequest parameter = RequestParameterUtil.getObjectOfRequestParameters(offset, limit, query, sortColumns, sortingDirection);
        try {
            inventoryResponse = userService.selectQuery(parameter);
        } catch (InvalidRequestParameteException ex) {
            inventoryResponse = new DtoResponse<>(false, ex.getMessage(), null, null);
        }
        return ResponseEntity.ok(inventoryResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoResponse<UserDto>> getById(@PathVariable long id) {
        DtoResponse<UserDto> response;
        try {
            response = new DtoResponse<>(true, "", 1L, List.of(userService.findById(id)));
        } catch (NotFoundException ex) {
            response = new DtoResponse<>(false, ex.getMessage(), null, null);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity<DtoResponse<UserDto>> update(@PathVariable long id,
                                                            @RequestBody UserUpdateRequest request) {
        DtoResponse<UserDto> response;
        try {
            response = new DtoResponse<>(true, "", 1L, List.of(userService.update(id, request)));
        } catch (NotFoundException ex) {
            response = new DtoResponse<>(false, ex.getMessage(), null, null);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<DtoResponse<UserDto>> add(@RequestBody UserUpdateRequest request) {
        DtoResponse<UserDto> response;
        try {
            response = new DtoResponse<>(true, "", 1L, List.of(userService.add(request)));
        } catch (NotFoundException ex) {
            response = new DtoResponse<>(false, ex.getMessage(), null, null);
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DtoResponse<UserDto>> deleteById(@PathVariable long id) {
        DtoResponse<UserDto> response;
        try {
            userService.deleteById(id);
            response = new DtoResponse<>(true, "Запись удалена", null, null);
        } catch (NotFoundException ex) {
            response = new DtoResponse<>(false, ex.getMessage(), null, null);
        }
        return ResponseEntity.ok(response);
    }

}
