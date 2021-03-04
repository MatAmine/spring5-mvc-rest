package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.api.v1.model.CategoryListDTO;
import guru.springframework.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CategoryController.BASE_URL)
@Tag(name = "Category", description = "request list to interract with categories")
public class CategoryController {

    public static final String BASE_URL = "/api/v1/categories";
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Get all the categories")
    @ApiResponse(responseCode = "200",
            description = "Categories loaded",
            content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CategoryListDTO.class))})
    @GetMapping
    public CategoryListDTO findAll() {
        return new CategoryListDTO(categoryService.findAll());
    }

    @Operation(summary = "Get category by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Category found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CategoryDTO.class))),
            @ApiResponse(responseCode = "404",
                    description = "No category found for the give name",
                    content = @Content)
    })

    @GetMapping("/{name}")
    public CategoryDTO findByName(@Parameter(description = "name of the category searched") @PathVariable String name) {
        return categoryService.findByName(name);
    }
}
