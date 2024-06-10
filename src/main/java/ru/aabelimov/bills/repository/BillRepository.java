package ru.aabelimov.bills.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.aabelimov.bills.entity.Bill;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> findAllByOrderStageIdOrderByIdDesc(Long orderStageId);

    @Query("SELECT b FROM Bill b WHERE b.orderStage.id = ?1 AND b.title LIKE %?2% ORDER BY b.id DESC")
    List<Bill> findAllByOrderStageIdAndTitleOrderByIdDesc(Long orderStageId, String title);
}
