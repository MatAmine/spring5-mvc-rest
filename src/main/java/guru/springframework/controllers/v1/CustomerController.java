package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.api.v1.model.CustomerListDTO;
import guru.springframework.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {

    public static final String BASE_URL = "/api/v1/customers";
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public CustomerListDTO findAll(){
        return new CustomerListDTO(customerService.findAll());
    }

    @GetMapping("/{id}")
    public CustomerDTO findById(@PathVariable Long id) {
        return customerService.findById(id);
    }

    @GetMapping("/firstname/{firstname}")
    public CustomerListDTO findByFirstname(@PathVariable String firstname) {
        return new CustomerListDTO(customerService.findByFirstname(firstname));
    }

    @GetMapping("/lastname/{lastname}")
    public CustomerListDTO findByLastname(@PathVariable String lastname) {
        return new CustomerListDTO(customerService.findByLastname(lastname));
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createNewCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.createNewCustomer(customerDTO);
    }

    @PutMapping("/update/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return customerService.updateCustomer(id, customerDTO);
    }

    @PatchMapping("/patch/{id}")
    public CustomerDTO patchCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return customerService.patchCustomer(id, customerDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteById(id);
    }
}
