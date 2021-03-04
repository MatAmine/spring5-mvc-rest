package guru.springframework;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Shop documentation", version = "1.0", description = "REST routes documentation for the shop application"))
public class Spring5MvcRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring5MvcRestApplication.class, args);
    }

}
