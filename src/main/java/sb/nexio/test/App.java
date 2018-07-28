package sb.nexio.test;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import sb.nexio.test.domain.Product;
import sb.nexio.test.domain.User;
import sb.nexio.test.repository.ProductRepository;
import sb.nexio.test.repository.UserRepository;

@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(App.class, args);
    }
    
    @Bean
	public CommandLineRunner loadData(ProductRepository repository, BCryptPasswordEncoder byBCryptPasswordEncoder,
			UserRepository userRepository) {
		return (args) -> {
			userRepository.save(new User(1L, "admin", byBCryptPasswordEncoder.encode("98765"), "shirley beleno"));
			repository.save(new Product(1L, "001", "Milk", 30.50, "Milk", 1000));
			repository.save(new Product(2L, "002", "Eggs", 10.75, "Eggs", 1000));
			repository.save(new Product(3L, "003", "Beef", 150.25, "Beef", 1000));
		};
	}
}
