package sb.nexio.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import sb.nexio.test.controller.ProductController;
import sb.nexio.test.domain.Product;
import sb.nexio.test.repository.ProductRepository;
import sb.nexio.test.repository.UserRepository;
import sb.nexio.test.security.ConsSecurity;
import sb.nexio.test.service.ProductService;
import sb.nexio.test.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {
	
	@Autowired
	private MockMvc mvc;
		
	@MockBean
	private UserService userService;

	@MockBean
	private ProductService productService;
	
	@MockBean
	private ProductRepository productRepository;
	
	@MockBean
	private UserRepository userRepository;

	@Test
	public void findAllProductsTest() throws Exception {
		
		Product prod1 = new Product(10L, "010", "Book", 15.25, "Book", 1000);
		Product prod2 = new Product(11L, "011", "Notebook", 10.35, "Notebook", 1000);
		Optional<List<Product>> list = Optional.of(Arrays.asList(prod1, prod2));

		BDDMockito.given(productService.findAll()).willReturn(list);

		mvc.perform(get("/api/products")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + getToken("admin")))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].code").value(prod1.getCode()));
	}
	
	public synchronized String getToken(String username) {
		String token = Jwts.builder().setIssuedAt(new Date()).setIssuer(ConsSecurity.ISSUER_INFO).setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + ConsSecurity.TOKEN_EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, ConsSecurity.SUPER_SECRET_KEY).compact();
		return token;
	}

}
