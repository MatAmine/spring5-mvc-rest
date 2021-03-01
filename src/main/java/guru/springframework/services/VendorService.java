package guru.springframework.services;

import guru.springframework.api.v1.model.VendorDTO;

import java.util.List;

public interface VendorService {
    List<VendorDTO> findAll();
    VendorDTO findById(Long id);
    VendorDTO createNew(VendorDTO vendorDTO);
    VendorDTO updateOne(Long id, VendorDTO vendorDTO);
    Void deleteById(Long id);
}
