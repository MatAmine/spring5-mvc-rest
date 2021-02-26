package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.services.CustomerService;
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

import static guru.springframework.utils.CustomerGenerator.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
        customerDTO = getOneCustomerDTO();
    }

    @Test
    void findAll() throws Exception {


        doReturn(CustomerGenerator.getCustomerList()).when(customerService).findAll();

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

        mockMvc.perform(get(API_URL + "/lastname").param("lastname", BURTON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(1)))
                .andExpect(jsonPath("$.customers[0].firstname", equalTo(TIM)));
    }
    @Test
    void findByFirstname() throws Exception {
        doReturn(List.of(getOneCustomerDTO())).when(customerService).findByFirstname(TIM);

        mockMvc.perform(get(API_URL + "/firstname").param("firstname", TIM)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(1)))
                .andExpect(jsonPath("$.customers[0].lastname", equalTo(BURTON)));
    }

}