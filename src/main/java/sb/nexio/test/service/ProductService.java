package sb.nexio.test.service;

import java.util.List;
import java.util.Optional;

import sb.nexio.test.domain.Product;

public interface ProductService {

	public Optional<List<Product>> findAll();
	public Optional<Product> findById(Long id);
}
