package sb.nexio.test.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sb.nexio.test.domain.CartDetail;
import sb.nexio.test.domain.Order;
import sb.nexio.test.domain.User;
import sb.nexio.test.exception.StatusException;
import sb.nexio.test.service.UserService;
import sb.nexio.test.service.ShoppinCartService;

@RestController
@RequestMapping("/api")
public class ShoppingCartController {
	
	public static final Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private ShoppinCartService shoppingCartService;
	
	@RequestMapping(value = "/shoppingCarts", method = { RequestMethod.POST })
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> addToCart(@RequestBody Order order, Principal principal) {
		
		User user = userService.findByUsername(principal.getName());
		order.setIdUser(user.getId());
		try {
			shoppingCartService.add(order);
		} catch (StatusException e) {
			return new ResponseEntity<>(e.getMessage(), e.getStatus());
		}
		
		return new ResponseEntity<List<Order>>(shoppingCartService.findAllByUser(user.getId()), HttpStatus.OK);		
	}
	
	@RequestMapping(value = "/shoppingCarts", method = RequestMethod.DELETE)
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<?> delete(@RequestParam("id") Long idOrder) {		
		try {
			shoppingCartService.delete(idOrder);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (StatusException e) {
			return new ResponseEntity<>(e.getMessage(), e.getStatus());
		}
	}
	
	@RequestMapping(value = "/shoppingCarts", method = RequestMethod.GET)
	public ResponseEntity<?> getDetails (Principal principal) throws StatusException{
		
		User user = userService.findByUsername(principal.getName());		
		CartDetail cartDetail = shoppingCartService.findDetails(user.getId());		
		
		return cartDetail.getOrders().isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT):
			new ResponseEntity<CartDetail>(cartDetail, HttpStatus.OK);		
	}
	
	
	

}
