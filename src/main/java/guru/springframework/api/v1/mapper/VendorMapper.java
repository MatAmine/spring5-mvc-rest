package guru.springframework.api.v1.mapper;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;
import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static guru.springframework.controllers.v1.VendorController.VENDOR_V1_BASE_URL;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface VendorMapper {

    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

    VendorDTO entityToDTO(Vendor vendor);
    List<VendorDTO> entityListToDTOList(List<Vendor> vendorList);

    Vendor DTOtoEntity(VendorDTO vendorDTO);
    List<Vendor> DTOListToEntityList(List<VendorDTO> vendorDTOList);

    @AfterMapping
    default void addVendorUrl(Vendor vendor, @MappingTarget VendorDTO vendorDTO) {
        vendorDTO.setVendorUrl(VENDOR_V1_BASE_URL + "/" + vendor.getId());
    }
}
