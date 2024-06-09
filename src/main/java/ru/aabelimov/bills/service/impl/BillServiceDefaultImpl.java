package ru.aabelimov.bills.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.aabelimov.bills.dto.CreateOrUpdateBillDto;
import ru.aabelimov.bills.entity.Bill;
import ru.aabelimov.bills.entity.OrderStage;
import ru.aabelimov.bills.entity.User;
import ru.aabelimov.bills.mapper.BillMapper;
import ru.aabelimov.bills.repository.BillRepository;
import ru.aabelimov.bills.service.BillService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BillServiceDefaultImpl implements BillService {

    private final BillRepository billRepository;
    private final BillMapper billMapper;

    @Value("${path.to.folder.with.bills}")
    private String fileDir;

    @Override
    public Bill createBill(CreateOrUpdateBillDto createOrUpdateBillDto,
                           OrderStage orderStage,
                           MultipartFile file) throws IOException {
        Bill bill = billMapper.toEntity(createOrUpdateBillDto);
        bill.setOrderStage(orderStage);
        bill.setPhotoPath(saveFile(file));
        return billRepository.save(bill);
    }

    @Override
    public Bill getBillById(Long id) {
        return billRepository.findById(id).orElseThrow(); // TODO :: exception
    }

    @Override
    public List<Bill> getBillsByOrderStageId(Long orderStageId) {
        return billRepository.findAllByOrderStageIdOrderByIdDesc(orderStageId);
    }

    @Override
    public List<Bill> getBillsByOrderStageIdAndTitle(Long orderStageId, String title) {
        return billRepository.findAllByOrderStageIdAndTitleOrderByIdDesc(orderStageId, title);
    }

    @Override
    public byte[] getImage(Long id) throws IOException {
        Bill bill = getBillById(id);
        return Files.readAllBytes(Path.of(bill.getPhotoPath()));
    }

    @Override
    public Bill updateBill(Long id, CreateOrUpdateBillDto dto, MultipartFile file) throws IOException {
        Bill bill = getBillById(id);
        System.out.println(dto.title());
        if (!file.isEmpty()) {
            Files.deleteIfExists(Path.of(bill.getPhotoPath()));
            bill.setPhotoPath(saveFile(file));
        }
        if (!dto.title().isBlank()) {
            bill.setTitle(dto.title());
        }
        if (!dto.description().isBlank()) {
            bill.setDescription(dto.description());
        }
        return billRepository.save(bill);
    }

    @Override
    public void removeBill(Bill bill) {
        try {
            Files.deleteIfExists(Path.of(bill.getPhotoPath()));
            billRepository.delete(bill);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isCorrectKey(Long id, String key) {
        if (key.equals("null")) {
            return false;
        }
        Bill bill = getBillById(id);
        User user = bill.getOrderStage().getOrder().getUser();
        return user.hashCode() == Integer.parseInt(key);
    }

    private String saveFile(MultipartFile file) throws IOException {
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String filename = null;
        Path filePath = null;

        do {
            filename = UUID.randomUUID().toString();
            filePath = Path.of(fileDir, filename + "." + extension);
        } while (Files.exists(filePath));

        Files.createDirectories(filePath.getParent());

        try (
                InputStream is = file.getInputStream();
                OutputStream os = Files.newOutputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
            return filePath.toString();
        }
    }
}
