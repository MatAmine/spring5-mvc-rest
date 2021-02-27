package guru.springframework.services;

import guru.springframework.api.v1.mapper.CategoryMapper;
import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.domain.Category;
import guru.springframework.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    public static final Long ID = 2L;
    public static final String NAME = "Jimmy";


    CategoryService categoryService;

    @Mock
    CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp() throws Exception {

        categoryService = new CategoryServiceImpl( categoryRepository, CategoryMapper.INSTANCE);
    }

    @Test
    void findAll() throws Exception {

        //given
        List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());
        categories.get(0).setName("Wong");
        categories.get(0).setId(1L);

        categories.get(1).setName("Zerg");
        categories.get(1).setId(2L);

        categories.get(2).setName("Cant");
        categories.get(2).setId(3L);

        when(categoryRepository.findAll()).thenReturn(categories);

        //when
        List<CategoryDTO> categoryDTOS = categoryService.findAll();

        //then
        assertEquals(3, categoryDTOS.size());

    }

    @Test
    void findByName() throws Exception {

        //given
        Category category = new Category();
        category.setId(ID);
        category.setName(NAME);

        when(categoryRepository.findByNameIgnoreCase(anyString())).thenReturn(category);

        //when
        CategoryDTO categoryDTO = categoryService.findByName(NAME);

        //then
        assertEquals(ID, categoryDTO.getId());
        assertEquals(NAME, categoryDTO.getName());

    }
}