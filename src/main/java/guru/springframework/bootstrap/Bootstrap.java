package guru.springframework.bootstrap;

import guru.springframework.domain.Category;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;


    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadCustomers();

        log.info("Categories Loaded = {}", categoryRepository.count());
        log.info("Customers Loaded = {}", customerRepository.count());

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