package guru.springframework.services;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return returnListOrNotFoundIfEmpty(customerRepository.findByFirstnameIgnoreCase(firstname));
    }

    @Override
    public List<CustomerDTO> findByLastname(String lastname) {
        return returnListOrNotFoundIfEmpty(customerRepository.findByLastnameIgnoreCase(lastname));

    }

    private List<CustomerDTO> returnListOrNotFoundIfEmpty(List<Customer> customerList) {
        if(!customerList.isEmpty()) {
            return customerMapper.entityListToDTOList(customerList);
        }
        else {
            throw new ResourceNotFoundException();
        }
    }

    @Override
    public CustomerDTO findById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::entityToDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        return saveAndReturnDTO(customerMapper.DTOtoEntity(customerDTO));
    }

    @Override
    public CustomerDTO updateCustomer(long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.DTOtoEntity(customerDTO);
        customer.setId(id);

        return saveAndReturnDTO(customer);

    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id).map(customer -> {
            if (customerDTO.getFirstname() != null) {
                customer.setFirstname(customerDTO.getFirstname());
            }
            if (customerDTO.getLastname() != null) {
                customer.setLastname(customerDTO.getLastname());
            }
            return customerMapper.entityToDTO(customerRepository.save(customer));
        })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }


    private CustomerDTO saveAndReturnDTO(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.entityToDTO(savedCustomer);
    }

}
