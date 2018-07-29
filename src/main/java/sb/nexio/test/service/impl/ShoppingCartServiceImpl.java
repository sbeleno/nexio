package sb.nexio.test.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sb.nexio.test.domain.CartDetail;
import sb.nexio.test.domain.Order;
import sb.nexio.test.domain.OrderDetail;
import sb.nexio.test.domain.Product;
import sb.nexio.test.exception.StatusException;
import sb.nexio.test.repository.ProductRepository;
import sb.nexio.test.repository.ShoppingCartRepository;
import sb.nexio.test.service.ShoppinCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppinCartService{
	
	@Autowired
	private ShoppingCartRepository cartRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	@Transactional
	public Order add(Order order) throws StatusException {
		
		//On valide que la quantité du produit soit supérieur ou égal à 1
		if(order.getQuantity() <= 0) {
			throw new StatusException("The quantity must be major or equal to 1", HttpStatus.BAD_REQUEST);
		}
		
		//On recherche la commande associée au produit
		List<Order> ordersSaved = findOrderByProduct(order.getIdUser(), order.getIdProduct());
		
		//Si n'existe pas, on crée la commande et on met à jour la quantité du produit en stock.
		//S'existe, on met à jour la commande et la quantité du produit en stock.
		if(ordersSaved.isEmpty()) {
			updateQuantityInStock(order);
			return cartRepository.save(order);
		} else {
			updateQuantityInStock(order);
			Order orderTmp = ordersSaved.get(0);
			orderTmp.setQuantity(orderTmp.getQuantity() + order.getQuantity());			
			cartRepository.flush();
			return orderTmp;			
		}		
	}	
		
	@Override
	public List<Order> findAllByUser(Long idUser) {
		//On utilise l'objet Specification pour ajouter les filtres à la recherche des commandes
		return cartRepository.findAll(new Specification<Order>() {

			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				predicates.add(criteriaBuilder.equal(root.get("idUser"), idUser));
				return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			}
		});
	}
	
	@Override
	@Transactional
	public void delete(Long idOrder) throws StatusException {
		Optional<Order> orderSaved = cartRepository.findById(idOrder);
		
		//On valide si la commane existe
		if(!orderSaved.isPresent()){
			throw new StatusException("Not found order:" + idOrder, HttpStatus.NOT_FOUND);
		}
		
		//On met à jour l'stock du produit et on supprime la commande
		Optional<Product> productFound = productRepository.findById(orderSaved.get().getIdProduct());
		productFound.get().setQtyStock(productFound.get().getQtyStock() + orderSaved.get().getQuantity());
		productRepository.flush();
		
		cartRepository.delete(orderSaved.get());
		cartRepository.flush();
	}	
	
	@Override
	public CartDetail findDetails(Long idUser) throws StatusException {
		
		List<Order> listOrders = findAllByUser(idUser);
		List<OrderDetail> details = new ArrayList<OrderDetail>();
		CartDetail cartDetail = new CartDetail();
		double total = 0.0;
		
		//On valide s'ils existent commandes pour l'utilisateur authentifié
		if (listOrders.isEmpty()) {
			throw new StatusException("No details", HttpStatus.NO_CONTENT);
		}
		
		//On construit un objet CartDetail en fonction des commandes sauvegardés
		for (Order order: listOrders ) {
			Optional<Product> productFound = productRepository.findById(order.getIdProduct());
			
			Double subtotal = productFound.get().getPrice() * order.getQuantity();
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setIdProduct(order.getIdProduct());
			orderDetail.setName(productFound.get().getName());
			orderDetail.setQuantity(order.getQuantity());
			orderDetail.setPrice(productFound.get().getPrice());
			orderDetail.setSubtotal(subtotal);
			
			total += subtotal;			
			details.add(orderDetail);
		}
		
		cartDetail.setOrders(details);
		cartDetail.setTotal(total);
		return cartDetail;
		
	}
	
	/**
	 * Met à jour la quantité du produit en stock
	 * @param order commande
	 * @throws StatusException si le produit n'existe pas 
	 * ou la quantité du produit en la commande est supérieur
	 * à la quantité du produit en stock
	 */
	private void updateQuantityInStock (Order order) throws StatusException {		
		
		Optional<Product> productFound = productRepository.findById(order.getIdProduct());
		
		//On valide si le produit existe
		if(!productFound.isPresent()) {
			throw new StatusException("Not found product with Id:" + order.getIdProduct(), HttpStatus.NOT_FOUND);
		}
		
		//On valide la quantité du produit en stock
		if(productFound.get().getQtyStock() >= order.getQuantity()) {
			productFound.get().setQtyStock(productFound.get().getQtyStock() - order.getQuantity());
			productRepository.flush();
		} else {
			throw new StatusException("There are not enough products with Id:" + order.getIdProduct(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Trouve la commande en fonction de l'utilisateur et de l'identifiant du produit
	 * @param idUser identifiant de l'utilisateur
	 * @param idProduct identifiant du produit
	 * @return liste de commandes
	 */
	private List<Order> findOrderByProduct(Long idUser, Long idProduct) {
		//On utilise l'objet Specification pour ajouter les filtres à la recherche des commandes 
		return cartRepository.findAll(new Specification<Order>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				predicates.add(criteriaBuilder.equal(root.get("idUser"), idUser));
				predicates.add(criteriaBuilder.equal(root.get("idProduct"), idProduct));
				return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			}
		});
	}
}
