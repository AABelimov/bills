package ru.aabelimov.bills.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.aabelimov.bills.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserId(Long userId);

    @Query("SELECT o FROM Order o WHERE o.user.id = ?1 AND o.title LIKE %?2%")
    List<Order> findAllByUserIdAndTitle(Long userId, String title);
}
