package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.api.v1.model.CustomerListDTO;
import guru.springframework.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping()
    public ResponseEntity<CustomerListDTO> findAll(){
        return new ResponseEntity<>(new CustomerListDTO(customerService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> findById(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/firstname")
    public ResponseEntity<CustomerListDTO> findByFirstname(@RequestParam String firstname) {
        return new ResponseEntity<>(new CustomerListDTO(customerService.findByFirstname(firstname)), HttpStatus.OK);
    }

    @GetMapping("/lastname")
    public ResponseEntity<CustomerListDTO> findByLastname(@RequestParam String lastname) {
        return new ResponseEntity<>(new CustomerListDTO(customerService.findByLastname(lastname)), HttpStatus.OK);
    }
}
