package ru.aabelimov.bills.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String photoPath;

    @OneToOne
    private OrderStage orderStage;
}
