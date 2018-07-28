package sb.nexio.test.service;

import java.util.List;

import sb.nexio.test.domain.Order;
import sb.nexio.test.domain.CartDetail;
import sb.nexio.test.exception.StatusException;

public interface ShoppinCartService {

	public Order add(Order order) throws StatusException;
	public List<Order> findAllByUser(Long idUser);
	void delete(Long idOrder) throws StatusException;
	public CartDetail findDetails(Long idUser) throws StatusException;

}
