package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.api.v1.model.VendorListDTO;
import guru.springframework.services.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(VendorController.VENDOR_V1_BASE_URL)
public class VendorController {

    public static final String VENDOR_V1_BASE_URL = "/api/v1/vendors";

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    public VendorListDTO findAll() {
        return new VendorListDTO(vendorService.findAll());
    }

    @GetMapping("/{id}")
    public VendorDTO findById(@PathVariable Long id) {
        return vendorService.findById(id);
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createNew(@RequestBody VendorDTO vendorDTO) {
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
