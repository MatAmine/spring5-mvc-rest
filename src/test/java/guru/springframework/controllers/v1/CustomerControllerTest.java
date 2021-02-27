package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.domain.Customer;
import guru.springframework.services.CustomerService;
import guru.springframework.services.ResourceNotFoundException;
import guru.springframework.utils.CustomerGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static guru.springframework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static guru.springframework.utils.CustomerGenerator.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    CustomerDTO customerDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(customerController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
        customerDTO = getOneCustomerDTO();
    }

    @Test
    void findAll() throws Exception {


        doReturn(CustomerGenerator.getCustomerDTOList()).when(customerService).findAll();

        mockMvc.perform(get(API_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(3)))
                .andExpect(jsonPath("$.customers[0].firstname", equalTo(MICHAEL)));
    }

    @Test
    void findById() throws Exception {
        doReturn(customerDTO).when(customerService).findById(anyLong());

        mockMvc.perform(get(API_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastname", equalTo(BURTON)));
    }

    @Test
    void findByLastname() throws Exception {
        doReturn(List.of(getOneCustomerDTO())).when(customerService).findByLastname(BURTON);

        mockMvc.perform(get(API_URL + "/lastname/" + BURTON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(1)))
                .andExpect(jsonPath("$.customers[0].firstname", equalTo(TIM)));
    }

    @Test
    void findByFirstname() throws Exception {
        doReturn(List.of(getOneCustomerDTO())).when(customerService).findByFirstname(TIM);

        mockMvc.perform(get(API_URL + "/firstname/" + TIM)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(1)))
                .andExpect(jsonPath("$.customers[0].lastname", equalTo(BURTON)));
    }

    @Test
    void createNewCustomer() throws Exception {
        CustomerDTO customerDTOParameter = getOneCustomerDTO();
        customerDTOParameter.setCustomerUrl(null);

        CustomerDTO customerDTOResult = getOneCustomerDTO();

        doReturn(customerDTOResult).when(customerService).createNewCustomer(customerDTOParameter);

        mockMvc.perform(post(API_URL + "/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTOParameter)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo(customerDTOParameter.getFirstname())))
                .andExpect(jsonPath("$.lastname", equalTo(customerDTOParameter.getLastname())))
                .andExpect(jsonPath("$.customer_url", equalTo(API_URL + "/1")));

    }

    @Test
    void updateCustomer() throws Exception {
        CustomerDTO customerDTOParameter = getOneCustomerDTO();
        customerDTOParameter.setCustomerUrl(null);

        CustomerDTO customerDTOResult = getOneCustomerDTO();

        doReturn(customerDTOResult).when(customerService).updateCustomer(1L, customerDTOParameter);

        mockMvc.perform(put(API_URL + "/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTOParameter)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(customerDTOParameter.getFirstname())))
                .andExpect(jsonPath("$.lastname", equalTo(customerDTOParameter.getLastname())))
                .andExpect(jsonPath("$.customer_url", equalTo(API_URL + "/1")));
    }

    @Test
    void patchCustomer() throws Exception {
        CustomerDTO customerDTOParameter = new CustomerDTO();
        customerDTOParameter.setFirstname("UPDATED FIRSTNAME");

        CustomerDTO customerDTOResult = getOneCustomerDTO();
        customerDTOResult.setFirstname("UPDATED FIRSTNAME");

        doReturn(customerDTOResult).when(customerService).patchCustomer(1L, customerDTOParameter);

        mockMvc.perform(patch(API_URL + "/patch/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTOParameter)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(customerDTOParameter.getFirstname())))
                .andExpect(jsonPath("$.lastname", equalTo(customerDTOResult.getLastname())))
                .andExpect(jsonPath("$.customer_url", equalTo(API_URL + "/1")));
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete(API_URL + "/delete/1")).andExpect(status().isOk());
    }

    @Test
    void findByLastnameNotFound() throws Exception {
        doThrow(ResourceNotFoundException.class).when(customerService).findByLastname(anyString());

        mockMvc.perform(get(API_URL + "/lastname/foo")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}