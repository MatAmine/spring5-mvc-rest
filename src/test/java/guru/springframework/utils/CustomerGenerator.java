package guru.springframework.utils;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.domain.Customer;

import java.util.List;

public final class CustomerGenerator {

    private CustomerGenerator() {
    }

    public static final String API_URL = "/api/v1/customers";
    public static final String MICHAEL = "Michael";
    public static final String JOHN = "John";
    public static final String JOSH = "Josh";
    public static final String FERREIRA = "Ferreira";
    public static final String TIM = "Tim";
    public static final String BURTON = "Burton";


    public static List<CustomerDTO> getCustomerList() {

        CustomerDTO customer1 = new CustomerDTO();
        CustomerDTO customer2 = new CustomerDTO();
        CustomerDTO customer3 = new CustomerDTO();

        customer1.setCustomerUrl(API_URL + "/1");
        customer1.setFirstname(MICHAEL);
        customer1.setLastname(JOHN);

        customer1.setCustomerUrl(API_URL + "/2");
        customer2.setFirstname(JOSH);
        customer2.setLastname(FERREIRA);

        customer1.setCustomerUrl(API_URL + "/3");
        customer3.setFirstname(TIM);
        customer3.setLastname(BURTON);

        return List.of(customer1, customer2, customer3);
    }

    public static Customer getOneCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setLastname(BURTON);
        customer.setFirstname(TIM);
        return customer;

    }

    public static CustomerDTO getOneCustomerDTO() {
        CustomerDTO customer = new CustomerDTO();
        customer.setLastname(BURTON);
        customer.setFirstname(TIM);
        customer.setCustomerUrl(API_URL + "/" + 1);

        return customer;

    }
}
