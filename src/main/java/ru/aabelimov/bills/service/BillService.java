package ru.aabelimov.bills.service;

import org.springframework.web.multipart.MultipartFile;
import ru.aabelimov.bills.dto.CreateOrUpdateBillDto;
import ru.aabelimov.bills.entity.Bill;
import ru.aabelimov.bills.entity.OrderStage;

import java.io.IOException;
import java.util.List;

public interface BillService {
    Bill createBill(CreateOrUpdateBillDto createOrUpdateBillDto, OrderStage orderStage, MultipartFile file) throws IOException;

    Bill getBillById(Long id);

    List<Bill> getBillsByOrderStageId(Long orderStageId);

    List<Bill> getBillsByOrderStageIdAndTitle(Long orderStageId, String title);

    byte[] getImage(Long id) throws IOException;

    void removeBill(Bill bill);
}
