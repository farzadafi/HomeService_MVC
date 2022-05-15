package repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository<Orders> extends JpaRepository<Orders,Integer> {
}
