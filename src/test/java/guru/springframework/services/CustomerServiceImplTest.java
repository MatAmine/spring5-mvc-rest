package guru.springframework.services;

import guru.springframework.api.v1.mapper.CategoryMapper;
import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    private static final String MICHAEL = "Michael";
    private static final String JOHN = "John";
    private static final String JOSH = "Josh";
    private static final String FERREIRA = "Ferreira";
    private static final String TIM = "Tim";
    private static final String BURTON = "Burton";

    private static final String URL = "/api/v1/customers";

    @Mock
    CustomerRepository customerRepository;

    CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerServiceImpl( customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    void findAll() {
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        Customer customer3 = new Customer();

        customer1.setId(1L);
        customer1.setFirstname(MICHAEL);
        customer1.setLastname(JOHN);

        customer2.setId(2L);
        customer2.setFirstname(JOSH);
        customer2.setLastname(FERREIRA);

        customer3.setId(3L);
        customer3.setFirstname(TIM);
        customer3.setLastname(BURTON);
        doReturn(List.of(customer1, customer2, customer3)).when(customerRepository).findAll();

        List<CustomerDTO> resultList = customerService.findAll();

        assertEquals(3, resultList.size());
        assertEquals(JOHN, resultList.get(0).getLastname());
        assertEquals(URL + "/" + 2, resultList.get(1).getCustomerUrl());
        assertEquals(TIM, resultList.get(2).getFirstname());


    }

}