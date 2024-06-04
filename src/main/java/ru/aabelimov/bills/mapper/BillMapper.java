package ru.aabelimov.bills.mapper;

import org.springframework.stereotype.Component;
import ru.aabelimov.bills.dto.CreateOrUpdateBillDto;
import ru.aabelimov.bills.entity.Bill;

@Component
public class BillMapper {

    public Bill toEntity(CreateOrUpdateBillDto dto) {
        Bill bill = new Bill();
        bill.setTitle(dto.title());
        bill.setDescription(dto.description());
        return bill;
    }
}
