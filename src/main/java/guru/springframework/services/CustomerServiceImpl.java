package guru.springframework.services;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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
        return customerMapper.entityListToDTOList(customerRepository.findByFirstname(firstname));
    }

    @Override
    public List<CustomerDTO> findByLastname(String lastname) {
        return customerMapper.entityListToDTOList(customerRepository.findByLastname(lastname));
    }

    @Override
    public CustomerDTO findById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::entityToDTO)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer savedCustomer = customerRepository.save(customerMapper.DTOtoEntity(customerDTO));
        return customerMapper.entityToDTO(savedCustomer);
    }
}
