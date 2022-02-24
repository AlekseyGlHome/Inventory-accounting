package ru.home.inventoryaccounting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.home.inventoryaccounting.service.InventoryService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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

//    @Test
//    void getAllInventory() throws Exception {
//        DtoResponse<InventoryDto> response = new DtoResponse<>();
//        response.setNumberOfRecord(1L);
//        List<InventoryDto> dtoList = new ArrayList<>();
//        InventoryFolderDto inventoryFolderDTO = InventoryFolderDto.builder().id(1L).isDeleted(false).name("Стаканы").build();
//        UnitDto unitDTO = UnitDto.builder().id(1L).isDeleted(false).name("шт").build();
//        InventoryDto inventoryDTO = InventoryDto.builder()
//                .id(1)
//                .isDeleted(false)
//                .name("Стакан")
//                .folder(inventoryFolderDTO)
//                .unit(unitDTO)
//                .build();
//        dtoList.add(inventoryDTO);
//        response.setData(dtoList);
//        String json = mapper.writeValueAsString(response);
//        Mockito.when(inventoryService.findByNameLikeAndFolderId(new ParameterRequest(0,10,"",0, new String[]{"name"}, SortingDirection.ASC))).thenReturn(response);
//        mockMvc.perform(get("/inventory?offset=0&limit=10&query=&folderId=")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("numberOfRecord").value("1"))
//                .andExpect(content().string(containsString(json)));
//    }
}
