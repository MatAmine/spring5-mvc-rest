package guru.springframework.services;

import guru.springframework.api.v1.model.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> findAll();
    List<CustomerDTO> findByFirstname(String firstname);
    List<CustomerDTO> findByLastName(String lastname);
    CustomerDTO findById(Long id);
}
