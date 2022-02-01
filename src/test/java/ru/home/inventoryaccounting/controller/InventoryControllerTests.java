package ru.home.inventoryaccounting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.home.inventoryaccounting.api.response.DTOResponse;
import ru.home.inventoryaccounting.domain.DTO.InventoryDTO;
import ru.home.inventoryaccounting.domain.DTO.InventoryFolderDTO;
import ru.home.inventoryaccounting.domain.DTO.UnitDTO;
import ru.home.inventoryaccounting.service.InventoryService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class InventoryControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private InventoryService inventoryService;

    @Test
    void getAllInventory() throws Exception {
        DTOResponse<InventoryDTO> response = new DTOResponse<>();
        response.setNumberOfRecord(1);
        List<InventoryDTO> dtoList = new ArrayList<>();
        InventoryFolderDTO inventoryFolderDTO = InventoryFolderDTO.builder().id(1L).deleted(false).name("Стаканы").build();
        UnitDTO unitDTO = UnitDTO.builder().id(1L).deleted(false).name("шт").build();
        InventoryDTO inventoryDTO = InventoryDTO.builder()
                .id(1)
                .deleted(false)
                .name("Стакан")
                .folder(inventoryFolderDTO)
                .unit(unitDTO)
                .build();
        dtoList.add(inventoryDTO);
        response.setData(dtoList);
        String json = mapper.writeValueAsString(response);
        Mockito.when(inventoryService.findByNameLikeAndFolderId(0, 10, "", 0)).thenReturn(response);
        mockMvc.perform(get("/inventory?offset=0&limit=10&query=&folderId=")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("numberOfRecord").value("1"))
                .andExpect(content().string(containsString(json)));
    }
}