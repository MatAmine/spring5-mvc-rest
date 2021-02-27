package guru.springframework.api.v1.mapper;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.domain.Customer;
import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static guru.springframework.controllers.v1.CustomerController.BASE_URL;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO entityToDTO(Customer customer);
    List<CustomerDTO> entityListToDTOList(List<Customer> customerList);

    Customer DTOtoEntity(CustomerDTO customerDTO);
    List<Customer> DTOListToEntityList(List<CustomerDTO> customerDTOList);

    @AfterMapping
    default void addCustomerUrl(Customer customer, @MappingTarget CustomerDTO customerDTO) {
        customerDTO.setCustomerUrl(BASE_URL + "/" + customer.getId());
    }
}
