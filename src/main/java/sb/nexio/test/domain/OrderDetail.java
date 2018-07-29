package sb.nexio.test.domain;

/**
 * Object qui gère les détails des commandes.
 * @author Shirley Beleno
 *
 */
public class OrderDetail {
		
	private Long idProduct;
	private Long idOrder;
	private String name;
	private Integer quantity;
	private Double price;
	private Double subtotal;
	
	public OrderDetail() {
		
	}
	
	public OrderDetail(Long idProduct, Long idOrder, String name, Integer quantity, Double price, Double subtotal) {
		this.idProduct = idProduct;
		this.idOrder = idOrder;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.subtotal = subtotal;
	}
	
	public Long getIdProduct() {
		return idProduct;
	}
	
	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}		
	
	public Long getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(Long idOrder) {
		this.idOrder = idOrder;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getSubtotal() {
		return subtotal;
	}
	
	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

}
