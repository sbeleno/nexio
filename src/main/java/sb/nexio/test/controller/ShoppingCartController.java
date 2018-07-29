package sb.nexio.test.controller;

import java.security.Principal;
import java.util.List;

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

/**
 * Controller qui permet d'ajouter et d'enlever les produits du panier,
 * permet de consulter les details du panier. 
 * @author Shirley Beleno
 *  
 */
@RestController
@RequestMapping("/api")
public class ShoppingCartController {
	
	public static final Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private ShoppinCartService shoppingCartService;
	
	@RequestMapping(value = "/shoppingCarts", method = { RequestMethod.POST })
	public ResponseEntity<?> addToCart(@RequestBody Order order, Principal principal) {
		
		User user = userService.findByUsername(principal.getName());
		order.setIdUser(user.getId());
		try {
			shoppingCartService.add(order);
			logger.info("Nous avons ajouté le produit au panier");
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
			logger.info("Nous avons supprimé le produit au panier");
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (StatusException e) {
			return new ResponseEntity<>(e.getMessage(), e.getStatus());
		}
	}
	
	@RequestMapping(value = "/shoppingCarts", method = RequestMethod.GET)
	public ResponseEntity<?> getDetails (Principal principal) throws StatusException{
		
		User user = userService.findByUsername(principal.getName());		
		CartDetail cartDetail = shoppingCartService.findDetails(user.getId());		
		
		if(cartDetail.getOrders().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			logger.info("Détails des produits du panier");
			return new ResponseEntity<CartDetail>(cartDetail, HttpStatus.OK);		
		}			
	}
}
