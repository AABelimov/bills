package ru.aabelimov.bills.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.aabelimov.bills.dto.CreateOrUpdateBillDto;
import ru.aabelimov.bills.entity.Bill;
import ru.aabelimov.bills.entity.OrderStage;
import ru.aabelimov.bills.service.BillService;
import ru.aabelimov.bills.service.OrderStageService;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("bills")
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;
    private final OrderStageService orderStageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String createBill(CreateOrUpdateBillDto createOrUpdateBillDto,
                             @RequestParam MultipartFile file) throws IOException {
        OrderStage orderStage = orderStageService.getOrderStageById(createOrUpdateBillDto.orderStageId());
        Bill bill = billService.createBill(createOrUpdateBillDto, orderStage, file);
        return "redirect:/bills/order-stage/%d".formatted(bill.getOrderStage().getId());
    }

    @GetMapping("order-stage/{orderStageId}")
    @PreAuthorize("@orderStageServiceDefaultImpl.isCorrectKey(#orderStageId, #key) or hasAuthority('ROLE_ADMIN')")
    public String getBillsByOrderStageId(@PathVariable Long orderStageId,
                                         @RequestParam(required = false) String title,
                                         @RequestParam(required = false) Integer key,
                                         Model model) {
        List<Bill> bills;
        if (title == null) {
            bills = billService.getBillsByOrderStageId(orderStageId);
        } else {
            bills = billService.getBillsByOrderStageIdAndTitle(orderStageId, title);
        }
        model.addAttribute("orderStage", orderStageService.getOrderStageById(orderStageId));
        model.addAttribute("bills", bills);
        model.addAttribute("key", key);
        return "bill/bills";
    }

    @GetMapping("{id}")
    @PreAuthorize("@billServiceDefaultImpl.isCorrectKey(#id, #key) or hasAuthority('ROLE_ADMIN')")
    public String getBill(@PathVariable Long id,
                          @RequestParam(required = false) String key,
                          Model model) {
        model.addAttribute("bill", billService.getBillById(id));
        return "bill/bill";
    }

    @GetMapping(value = "{id}/image", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] getImage(@PathVariable Long id) throws IOException {
        return billService.getImage(id);
    }

    @DeleteMapping("{id}")
    public String removeBill(@PathVariable Long id) {
        Bill bill = billService.getBillById(id);
        billService.removeBill(bill);
        return "redirect:/bills/order-stage/%d".formatted(bill.getOrderStage().getId());
    }
}
