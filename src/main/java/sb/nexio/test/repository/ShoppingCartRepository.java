package sb.nexio.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import sb.nexio.test.domain.Order;

@Repository
public interface ShoppingCartRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order>{

}
