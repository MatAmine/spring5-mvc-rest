package guru.springframework.services;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CustomerRepository;
import guru.springframework.utils.CustomerGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static guru.springframework.utils.CustomerGenerator.getOneCustomer;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    Customer customer;

    @BeforeEach
    void setUp() {
        customerService = new CustomerServiceImpl( customerRepository, CustomerMapper.INSTANCE);
        customer = getOneCustomer();
    }

    @Test
    void findAll() {
        doReturn(CustomerGenerator.getCustomerList()).when(customerRepository).findAll();

        List<CustomerDTO> resultList = customerService.findAll();

        assertEquals(3, resultList.size());
        assertEquals(JOHN, resultList.get(0).getLastname());
        assertEquals(URL + "/" + 2, resultList.get(1).getCustomerUrl());
        assertEquals(TIM, resultList.get(2).getFirstname());
    }

    @Test
    void findById() {
        doReturn(Optional.of(customer)).when(customerRepository).findById(1L);

        CustomerDTO customerDTO = customerService.findById(1L);

        assertEquals(TIM, customerDTO.getFirstname());
        assertEquals(BURTON, customerDTO.getLastname());
        assertEquals(URL + "/" + 1, customerDTO.getCustomerUrl());
    }

    @Test
    void findByLastname() {
        doReturn(Collections.singletonList(customer)).when(customerRepository).findByLastname(BURTON);

        List<CustomerDTO> customerDTOList = customerService.findByLastname(BURTON);
        assertThat(customerDTOList, hasSize(1));
        assertEquals(TIM, customerDTOList.get(0).getFirstname());
        assertEquals(URL + "/" + 1, customerDTOList.get(0).getCustomerUrl());
    }
    @Test
    void findByFirstname() {
        doReturn(Collections.singletonList(customer)).when(customerRepository).findByFirstname(TIM);

        List<CustomerDTO> customerDTOList = customerService.findByFirstname(TIM);
        assertThat(customerDTOList, hasSize(1));
        assertEquals(TIM, customerDTOList.get(0).getFirstname());
        assertEquals(URL + "/" + 1, customerDTOList.get(0).getCustomerUrl());
    }

}