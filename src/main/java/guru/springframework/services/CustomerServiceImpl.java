package guru.springframework.services;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.repositories.CustomerRepository;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> findAll() {
        return customerMapper.entityListToDTOList(customerRepository.findAll());
    }

    @Override
    public List<CustomerDTO> findByFirstname(String firstname) {
        return null;
    }

    @Override
    public List<CustomerDTO> findByLastName(String lastname) {
        return null;
    }

    @Override
    public CustomerDTO findById(Long id) {
        return null;
    }
}
