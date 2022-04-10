package com.stortor.spring.web.core.repositories;

import com.stortor.spring.web.core.entity.Order;
import com.stortor.spring.web.core.enums.OrderStateEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Cacheable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where o.username = ?1")
    List<Order> findAllByUsername(String username);

    @Query("select o from Order o where o.id = ?1 and o.state = 'CREATED'")
    Optional<Order> findByIdAndState_Created(Long orderId);

}
