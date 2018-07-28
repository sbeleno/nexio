package sb.nexio.test.domain;

public class OrderDetail {
		
	private Long idProduct;
	private String name;
	private Integer quantity;
	private Double subtotal;
	
	public OrderDetail() {
		
	}
	
	public OrderDetail(Long idProduct, String name, Integer quantity, Double subtotal) {
		this.idProduct = idProduct;
		this.name = name;
		this.quantity = quantity;
		this.subtotal = subtotal;
	}
	
	public Long getIdProduct() {
		return idProduct;
	}
	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
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
	public Double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

}
