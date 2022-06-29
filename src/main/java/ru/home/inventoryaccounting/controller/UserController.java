package ru.home.inventoryaccounting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.home.inventoryaccounting.api.request.RequestParametersForDirectories;
import ru.home.inventoryaccounting.api.request.UserRequest;
import ru.home.inventoryaccounting.api.response.DtoResponse;
import ru.home.inventoryaccounting.domain.dto.UserDto;
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
            @RequestParam(name = "sortColumns", defaultValue = "name") String[] sortColumns,
            @RequestParam(name = "sortingDirection", defaultValue = "ASC") String sortingDirection) {

        RequestParametersForDirectories parameter = RequestParameterUtil.getObjectOfRequestParameters(offset, limit, query, sortColumns, sortingDirection);
        return ResponseEntity.ok(userService.selectQuery(parameter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoResponse<UserDto>> getById(@PathVariable long id) {
        return ResponseEntity.ok(new DtoResponse<>(1L, List.of(userService.findById(id))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DtoResponse<UserDto>> update(@PathVariable long id,
                                                       @RequestBody UserRequest request) {
        return ResponseEntity.ok(new DtoResponse<>(1L, List.of(userService.update(id, request))));
    }

    @PostMapping()
    public ResponseEntity<DtoResponse<UserDto>> add(@RequestBody UserRequest request) {
        return ResponseEntity.ok(new DtoResponse<>(1L, List.of(userService.add(request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id) {
        userService.deleteById(id);
        return ResponseEntity.ok("Запись удалена");
    }

}
