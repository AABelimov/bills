package ru.aabelimov.bills.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.aabelimov.bills.entity.OrderStage;

import java.util.List;

public interface OrderStageRepository extends JpaRepository<OrderStage, Long> {

    List<OrderStage> findAllByOrderIdOrderByIdDesc(Long orderId);

    @Query("SELECT os FROM OrderStage os WHERE os.order.id = ?1 AND os.title LIKE %?2% ORDER BY os.id DESC")
    List<OrderStage> findAllByOrderIdAndTitleOrderByIdDesc(Long orderId, String title);
}
