package guru.springframework.bootstrap;

import guru.springframework.domain.Category;
import guru.springframework.domain.Customer;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.CustomerRepository;
import guru.springframework.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadCustomers();
        loadVendors();

        log.info("Categories Loaded = {}", categoryRepository.count());
        log.info("Customers Loaded = {}", customerRepository.count());
        log.info("Vendors Loaded = {}", vendorRepository.count());

    }

    private void loadVendors() {
        Vendor vendor = new Vendor();
        vendor.setName("Fnac");

        Vendor vendor2 = new Vendor();
        vendor2.setName("Boulanger");

        Vendor vendor3 = new Vendor();
        vendor3.setName("Darty");

        vendorRepository.saveAll(List.of(vendor, vendor2,vendor3));
    }

    private void loadCustomers() {
        Customer customer1 = new Customer();
        customer1.setLastname("BURTON");
        customer1.setFirstname("Tim");

        Customer customer2 = new Customer();
        customer2.setLastname("STARK");
        customer2.setFirstname("Tony");

        Customer customer3 = new Customer();
        customer3.setLastname("VISION");
        customer3.setFirstname("Wanda");

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);
    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);
    }
}