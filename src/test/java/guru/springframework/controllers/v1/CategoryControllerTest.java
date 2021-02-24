package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    public static final String JIM = "Jim";
    public static final String BOB = "Bob";
    public static final String API_URL = "/api/v1/categories/";

    @Mock
    CategoryService categoryService;

    MockMvc mockMvc;

    @InjectMocks
    CategoryController categoryController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void testFindAll() throws Exception {
        CategoryDTO category = CategoryDTO.builder().id(1L).name(JIM).build();
        CategoryDTO category2 = CategoryDTO.builder().id(2L).name(BOB).build();

        List<CategoryDTO> categories = List.of(category, category2);

        doReturn(categories).when(categoryService).findAll();

    mockMvc.perform(get(API_URL)
        .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.categories", hasSize(2)))
            .andExpect(jsonPath("$.categories[0].name", equalTo(JIM)))
            .andExpect(jsonPath("$.categories[1].id", equalTo(2)));
    }

    @Test
    void testFindByName() throws Exception {
        CategoryDTO category = CategoryDTO.builder().id(1L).name(JIM).build();

        doReturn(category).when(categoryService).findByName(JIM);

        mockMvc.perform(get(API_URL + JIM)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(JIM)))
                .andExpect(jsonPath("$.id", equalTo(1)));
    }

}