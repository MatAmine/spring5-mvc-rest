package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.api.v1.model.VendorListDTO;
import guru.springframework.services.VendorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(VendorController.VENDOR_V1_BASE_URL)
@Tag(name = "Vendor", description = "Requests to interact with the vendor controller")
public class VendorController {

    public static final String VENDOR_V1_BASE_URL = "/api/v1/vendors";

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @Operation(summary = "Get all the vendors", description = "Fetch all the existing vendors")
    @ApiResponse(responseCode = "200", description = "Vendors returned",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VendorListDTO.class)))
    @GetMapping
    public VendorListDTO findAll() {
        return new VendorListDTO(vendorService.findAll());
    }

    @Operation(summary = "Get vendor by ID", description = "Get a vendor corresponding to a given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vendor found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VendorDTO.class))),
            @ApiResponse(responseCode = "404", description = "No corresponding vendor found", content = @Content)
    })
    @GetMapping("/{id}")
    public VendorDTO findById(@Parameter(description = "ID of the searched vendor")  @PathVariable Long id) {
        return vendorService.findById(id);
    }

    @Operation(summary = "Create a new vendor", description = "Provide information that will be used to create a new vendor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New vendor created",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VendorDTO.class)))
    })
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createNew(@io.swagger.v3.oas.annotations.parameters.RequestBody( description = "VendorDTO object that will be used to create a new vendor")
                                   @RequestBody VendorDTO vendorDTO) {
        return vendorService.createNew(vendorDTO);
    }

    @PutMapping("/update/{id}")
    public VendorDTO updateOne(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {
        return vendorService.updateOne(id, vendorDTO);
    }

    @DeleteMapping("/delete/{id}")
    public Void deleteById(@PathVariable Long id) {
        return vendorService.deleteById(id);
    }
}
