package guru.springframework.services;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<VendorDTO> findAll() {
        return vendorMapper.entityListToDTOList(vendorRepository.findAll());
    }

    @Override
    public VendorDTO findById(Long id) {
        return vendorRepository.findById(id)
                .map(vendorMapper::entityToDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO createNew(VendorDTO vendorDTO) {
        Vendor savedVentor = vendorRepository.save(vendorMapper.DTOtoEntity(vendorDTO));
        return vendorMapper.entityToDTO(savedVentor);
    }

    @Override
    public VendorDTO updateOne(Long id, VendorDTO vendorDTO) {

        if(vendorRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("No corresponding vendor");
        }
        Vendor vendor = vendorMapper.DTOtoEntity(vendorDTO);
        vendor.setId(id);
        Vendor savedVendor = vendorRepository.save(vendor);
        return vendorMapper.entityToDTO(savedVendor);
    }

    @Override
    public Void deleteById(Long id) {
        vendorRepository.deleteById(id);
        return null;
    }
}
