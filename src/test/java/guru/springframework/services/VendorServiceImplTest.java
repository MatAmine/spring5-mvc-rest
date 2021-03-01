package guru.springframework.services;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.mapper.VendorMapperImpl;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static guru.springframework.controllers.v1.VendorController.VENDOR_V1_BASE_URL;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VendorServiceImplTest {

    private static final String VENDOR_NAME = "name";


    @Mock
    VendorRepository vendorRepository;

    VendorServiceImpl vendorService;

    @BeforeEach
    void setUp() {
        vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
    }

    @Test
    void findAll() {
        List<Vendor> vendorList = List.of(new Vendor(), new Vendor(), new Vendor());

        doReturn(vendorList).when(vendorRepository).findAll();

        List<VendorDTO> resultList = vendorService.findAll();
        verify(vendorRepository, times(1)).findAll();

        assertThat(resultList, hasSize(3));
    }

    @Test
    void findById() {
        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setName(VENDOR_NAME);

        doReturn(Optional.of(vendor)).when(vendorRepository).findById(1L);

        VendorDTO result = vendorService.findById(1L);

        verify(vendorRepository, times(1)).findById(1L);
        assertEquals(VENDOR_V1_BASE_URL + "/1", result.getVendorUrl());
        assertEquals(VENDOR_NAME, result.getName());
    }

    @Test
    void createNew() {

        VendorDTO newVendorDTO = new VendorDTO();
        newVendorDTO.setName(VENDOR_NAME);

        Vendor savedVendor = new Vendor();
        savedVendor.setId(1L);
        savedVendor.setName(VENDOR_NAME);

        doReturn(savedVendor).when(vendorRepository).save(any(Vendor.class));

        ArgumentCaptor<Vendor> captor = ArgumentCaptor.forClass(Vendor.class);
        VendorDTO result = vendorService.createNew(newVendorDTO);


        verify(vendorRepository, times(1)).save(captor.capture());
        assertEquals(VENDOR_NAME, captor.getValue().getName());
        assertEquals(VENDOR_V1_BASE_URL + "/1", result.getVendorUrl());
        assertEquals(VENDOR_NAME, result.getName());
    }

    @Test
    void updateOne() {
        VendorDTO toUpdateVendorDTO = new VendorDTO();
        toUpdateVendorDTO.setName(VENDOR_NAME);

        Vendor existingVendor = new Vendor();
        existingVendor.setId(1L);

        Vendor updatedVendor = new Vendor();
        updatedVendor.setName(VENDOR_NAME);
        updatedVendor.setId(1L);

        doReturn(Optional.of(existingVendor)).when(vendorRepository).findById(1L);
        doReturn(updatedVendor).when(vendorRepository).save(any());

        VendorDTO result = vendorService.updateOne(1L, toUpdateVendorDTO);
        assertEquals(VENDOR_V1_BASE_URL + "/1", result.getVendorUrl());
        assertEquals(updatedVendor.getName(), result.getName());
    }

    @Test
    void deleteById() {
        Long id = 1L;
        vendorService.deleteById(id);
        verify(vendorRepository, times(1)).deleteById(id);
    }
}