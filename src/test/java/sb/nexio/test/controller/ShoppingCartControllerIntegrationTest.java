package sb.nexio.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import sb.nexio.test.domain.Order;
import sb.nexio.test.domain.Product;
import sb.nexio.test.domain.User;
import sb.nexio.test.repository.ProductRepository;
import sb.nexio.test.repository.ShoppingCartRepository;
import sb.nexio.test.repository.UserRepository;
import sb.nexio.test.security.ConsSecurity;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integration-test.properties")
public class ShoppingCartControllerIntegrationTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ShoppingCartRepository cartRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	
	@Autowired
	private BCryptPasswordEncoder byBCryptPasswordEncoder;
	
	@Test
	public void getDetailsTest() throws Exception {
		createUserAndProducts();
		createOrders();
		mvc.perform(get("/api/shoppingCarts")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",
				"Bearer " + getToken("admin")))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.orders", Matchers.hasSize(2)));
	} 
	
	public synchronized String getToken(String username) {
		String token = Jwts.builder().setIssuedAt(new Date()).setIssuer(ConsSecurity.ISSUER_INFO).setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + ConsSecurity.TOKEN_EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, ConsSecurity.SUPER_SECRET_KEY).compact();
		return token;
	}
	
	private void createUserAndProducts() {
		userRepository.save(new User(1L, "admin", byBCryptPasswordEncoder.encode("98765"), "shirley beleno"));
		productRepository.save( new Product(10L, "010", "Book", 15.25, "Book", 1000));
		productRepository.save( new Product(11L, "011", "Notebook", 10.35, "Notebook", 1000));
	} 
	
	private void createOrders() {
		Order order = new Order();
		order.setId(1L);
		order.setIdProduct(10L);
		order.setIdUser(1L);
		order.setQuantity(1);
		cartRepository.save(order);
		
		order.setId(2L);
		order.setIdProduct(11L);
		order.setIdUser(1L);
		order.setQuantity(1);
		cartRepository.save(order);
	}
	
	

}
