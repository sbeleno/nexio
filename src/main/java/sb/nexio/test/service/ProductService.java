package sb.nexio.test.service;

import java.util.List;
import java.util.Optional;

import sb.nexio.test.domain.Product;

public interface ProductService {

	/**
	 * Permet de lister les produits.
	 * @return la liste de produits sauvegardés
	 */
	Optional<List<Product>> findAll();
	
	/**
	 * Permet de trouver un produit en fonction de son identifiant.
	 * @param id identifiant du produit
	 * @return le produit trouvé
	 */
	Optional<Product> findById(Long id);
}
