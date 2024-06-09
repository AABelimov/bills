package ru.aabelimov.bills.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "order_stages")
public class OrderStage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @OneToOne
    private Order order;
}
