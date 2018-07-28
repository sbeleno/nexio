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
		
		if(order.getQuantity() == 0) {
			throw new StatusException("The quantity must be major or equal to 1", HttpStatus.BAD_REQUEST);
		}
		
		List<Order> ordersSaved = findOrderByProduct(order.getIdUser(), order.getIdProduct());
		
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
		
		if(!orderSaved.isPresent()){
			throw new StatusException("Not found order:" + idOrder, HttpStatus.NOT_FOUND);
		}
		
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
		
		if (listOrders.isEmpty()) {
			throw new StatusException("No details", HttpStatus.NO_CONTENT);
		}
		
		for (Order order: listOrders ) {
			Optional<Product> productFound = productRepository.findById(order.getIdProduct());
			
			Double subtotal = productFound.get().getPrice() * order.getQuantity();
			OrderDetail orderTmp = new OrderDetail();
			orderTmp.setIdProduct(order.getIdProduct());
			orderTmp.setName(productFound.get().getName());
			orderTmp.setQuantity(order.getQuantity());
			orderTmp.setSubtotal(subtotal);
			
			total += subtotal;
			
			details.add(orderTmp);
		}
		
		cartDetail.setOrders(details);
		cartDetail.setTotal(total);
		return cartDetail;
		
	}
	
	private void updateQuantityInStock (Order order) throws StatusException {		
		
		Optional<Product> productFound = productRepository.findById(order.getIdProduct());
		
		if(!productFound.isPresent()) {
			throw new StatusException("Not found product with Id:" + order.getIdProduct(), HttpStatus.NOT_FOUND);
		}
		
		if(productFound.get().getQtyStock() >= order.getQuantity()) {
			productFound.get().setQtyStock(productFound.get().getQtyStock() - order.getQuantity());
			productRepository.flush();
		} else {
			throw new StatusException("There are not enough products with Id:" + order.getIdProduct(), HttpStatus.BAD_REQUEST);
		}
	}
	
	private List<Order> findOrderByProduct(Long idUser, Long idProduct) {
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
