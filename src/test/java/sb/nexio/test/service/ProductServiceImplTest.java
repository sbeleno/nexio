package sb.nexio.test.service;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import sb.nexio.test.domain.Product;
import sb.nexio.test.repository.ProductRepository;
import sb.nexio.test.service.impl.ProductServiceImpl;

@RunWith(SpringRunner.class)
public class ProductServiceImplTest {
	
	@TestConfiguration
	static class ProductServiceImplTestContextConfiguration {
		@Bean
		public ProductService productService() {
			return new ProductServiceImpl();
		}
	}
	
	@Autowired
	private ProductService productService;

	@MockBean
	private ProductRepository productRepository;
	
	@Before
	public void setUp() {
		Mockito.when(productRepository.findAll())
				.thenReturn(Arrays.asList(new Product(10L, "010", "Book", 15.25, "Book", 1000),
						new Product(11L, "011", "Notebook", 10.35, "Notebook", 1000)));

		Product product = new Product(10L, "010", "Book", 15.25, "Book", 1000);
		Mockito.when(productRepository.findById(10L)).thenReturn(Optional.of(product));
	}

	@Test
	public void whenFindProductIsPresent() {
		Optional<Product> product = productService.findById(10L);
		assertTrue("Not found Product", product.isPresent());
	}

	@Test
	public void whenFindAllProductEqualLenght() {
		Optional<List<Product>> list = productService.findAll();
		assertEquals(2, list.get().size());
	}

}
