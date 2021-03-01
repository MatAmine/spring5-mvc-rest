package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.services.VendorService;
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
import static guru.springframework.controllers.v1.VendorController.VENDOR_V1_BASE_URL;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class VendorControllerTest {

    private static final String VENDOR_NAME = "name";

    MockMvc mockMvc;

    @Mock
    VendorService vendorService;

    @InjectMocks
    VendorController vendorController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(vendorController).build();
    }

    @Test
    void findAll() throws Exception {
        List<VendorDTO> vendorDTOList = List.of(new VendorDTO(), new VendorDTO(), new VendorDTO());
        doReturn(vendorDTOList).when(vendorService).findAll();

        mockMvc.perform(get(VENDOR_V1_BASE_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(3)));
    }

    @Test
    void findById() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(VENDOR_NAME);
        vendorDTO.setVendorUrl(VENDOR_V1_BASE_URL + "/5");

        doReturn(vendorDTO).when(vendorService).findById(5L);

        mockMvc.perform(get(VENDOR_V1_BASE_URL + "/5").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO.getName())))
                .andExpect(jsonPath("$.vendor_url", equalTo(vendorDTO.getVendorUrl())));
    }

    @Test
    void createNew() throws Exception {
        VendorDTO newVendorDTO = new VendorDTO();
        newVendorDTO.setName(VENDOR_NAME);

        VendorDTO savedVendor = new VendorDTO();
        savedVendor.setName(VENDOR_NAME);
        savedVendor.setVendorUrl(VENDOR_V1_BASE_URL + "/2");

        doReturn(savedVendor).when(vendorService).createNew(newVendorDTO);

        mockMvc.perform(post(VENDOR_V1_BASE_URL + "/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newVendorDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(savedVendor.getName())))
                .andExpect(jsonPath("$.vendor_url", equalTo(savedVendor.getVendorUrl())));
    }

    @Test
    void updateOne() throws Exception {
        Long id = 1L;

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(VENDOR_NAME);

        VendorDTO updatedVendor = new VendorDTO();
        updatedVendor.setName(VENDOR_NAME);
        updatedVendor.setVendorUrl(VENDOR_V1_BASE_URL + "/" + id);

        doReturn(updatedVendor).when(vendorService).updateOne(id, vendorDTO);

        mockMvc.perform(put(VENDOR_V1_BASE_URL + "/update/" + id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(updatedVendor.getName())))
                .andExpect(jsonPath("$.vendor_url", equalTo(updatedVendor.getVendorUrl())));
    }

    @Test
    void deleteById() throws  Exception {
        mockMvc.perform(delete(VENDOR_V1_BASE_URL + "/delete/1"))
                .andExpect(status().isOk());
    }
}