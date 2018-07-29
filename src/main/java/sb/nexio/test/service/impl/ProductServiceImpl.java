package sb.nexio.test.service.impl;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sb.nexio.test.domain.Product;
import sb.nexio.test.repository.ProductRepository;
import sb.nexio.test.service.ProductService;

@Service("productService")
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productRepository;

	@Override
	@Transactional(readOnly=true)
	public Optional<List<Product>> findAll() {
		return Optional.of(productRepository.findAll());
	}
	
	@Override
	@Transactional(readOnly=true)
	public Optional<Product> findById(Long id) {
		return productRepository.findById(id);			 
	}
}
