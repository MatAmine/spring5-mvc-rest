package guru.springframework.api.v1.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class CategoryDTO {
    private Long id;
    private String name;
}
