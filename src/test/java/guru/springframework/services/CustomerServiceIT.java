package guru.springframework.services;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.bootstrap.Bootstrap;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.CustomerRepository;
import guru.springframework.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static guru.springframework.utils.CustomerGenerator.API_URL;
import static guru.springframework.utils.CustomerGenerator.getOneCustomerDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CustomerServiceIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VendorRepository vendorRepository;

    CustomerService customerService;

    @BeforeEach
    void setUp() throws Exception {
        System.out.println("Loading Customer Data");
        System.out.println(customerRepository.findAll().size());

        //setup data for testing
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run();

        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    void patchCustomerUpdateFirstname() throws Exception{
        CustomerDTO customerDTOParameter = new CustomerDTO();
        customerDTOParameter.setFirstname("UPDATED FIRSTNAME");

        final Long id = getCustomerIdValue();
        Customer originalCustomer = customerRepository.findById(id).get();
        String originalCustomerFirstname = originalCustomer.getFirstname();
        String originalCustomerLastname = originalCustomer.getLastname();
        Long originalCustomerId = originalCustomer.getId();
        assertNotNull(originalCustomer);

        CustomerDTO updatedCustomer = customerService.patchCustomer(id, customerDTOParameter);

        assertNotNull(updatedCustomer);
        assertNotEquals(originalCustomerFirstname, updatedCustomer.getFirstname());
        assertEquals("UPDATED FIRSTNAME", updatedCustomer.getFirstname());
        assertEquals(API_URL + "/" + originalCustomerId, updatedCustomer.getCustomerUrl());
        assertEquals(originalCustomerLastname, updatedCustomer.getLastname());

    }

    @Test
    void patchCustomerUpdateLastname() throws Exception{
        CustomerDTO customerDTOParameter = new CustomerDTO();
        customerDTOParameter.setLastname("UPDATED LASTNAME");

        final Long id = getCustomerIdValue();
        Customer originalCustomer = customerRepository.findById(id).get();
        String originalCustomerFirstname = originalCustomer.getFirstname();
        String originalCustomerLastname = originalCustomer.getLastname();
        Long originalCustomerId = originalCustomer.getId();
        assertNotNull(originalCustomer);

        CustomerDTO updatedCustomer = customerService.patchCustomer(id, customerDTOParameter);

        assertNotNull(updatedCustomer);
        assertNotEquals(originalCustomerLastname, updatedCustomer.getLastname());
        assertEquals("UPDATED LASTNAME", updatedCustomer.getLastname());
        assertEquals(API_URL + "/" + originalCustomerId, updatedCustomer.getCustomerUrl());
        assertEquals(originalCustomerFirstname, updatedCustomer.getFirstname());
    }

    private Long getCustomerIdValue() {
        return customerRepository.findAll().get(0).getId();
    }
}
