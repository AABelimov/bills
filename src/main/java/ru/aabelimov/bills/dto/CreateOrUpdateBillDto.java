package ru.aabelimov.bills.dto;

public record CreateOrUpdateBillDto(String title, String description, Long orderStageId) {
}
