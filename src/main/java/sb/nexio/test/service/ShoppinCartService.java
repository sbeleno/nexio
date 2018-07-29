package sb.nexio.test.service;

import java.util.List;

import sb.nexio.test.domain.Order;
import sb.nexio.test.domain.CartDetail;
import sb.nexio.test.exception.StatusException;

public interface ShoppinCartService {

	/**
	 * Ajoute le commande au panier
	 * @param order commande avec le produit
	 * @return commande sauvegardé
	 * @throws StatusException
	 */
	Order add(Order order) throws StatusException;
	
	/**
	 * Liste les commandes en fonction de l'utilisateur
	 * @param idUser identifiant de l'utilisateur
	 * @return liste de commandes
	 */
	List<Order> findAllByUser(Long idUser);
	
	/**
	 * Supprime les commandes
	 * @param idOrder identifiant de la commande
	 * @throws StatusException si la commande n'existe pas
	 */
	void delete(Long idOrder) throws StatusException;
	
	/**
	 * Trouve le détail du panier en fonction de l'utilisateur
	 * @param idUser identifiant de l'utilisateur
	 * @return le detail du panier
	 * @throws StatusException si une commande n'existe pas 
	 * pour l'utilisateur authentifié
	 */
	CartDetail findDetails(Long idUser) throws StatusException;

}
