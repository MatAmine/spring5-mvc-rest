package guru.springframework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class VendorDTO {

    @NotBlank
    private String name;

    @JsonProperty("vendor_url")
    private String vendorUrl;
}
