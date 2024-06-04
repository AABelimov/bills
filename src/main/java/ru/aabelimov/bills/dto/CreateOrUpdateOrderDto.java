package ru.aabelimov.bills.dto;

public record CreateOrUpdateOrderDto(String title, String description, Long userId) {
}
