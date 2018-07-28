package sb.nexio.test.service.impl;


import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	public static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

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
