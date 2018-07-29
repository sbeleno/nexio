package sb.nexio.test.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Object pour afficher les details du panier
 * @author Shirley Beleno
 *
 */
public class CartDetail {
	
	private List<OrderDetail> orders = new ArrayList<OrderDetail>();
	private Double total;
	
	public CartDetail() {
		
	}
	
	public CartDetail(List<OrderDetail> orders, Double total) {
		this.orders = orders;
		this.total = total;
	}

	public List<OrderDetail> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderDetail> orders) {
		this.orders = orders;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

}
