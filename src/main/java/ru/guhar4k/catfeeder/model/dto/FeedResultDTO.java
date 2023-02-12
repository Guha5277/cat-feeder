package ru.guhar4k.catfeeder.model.dto;

import lombok.*;

/**
 * Результат выдачи корма
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FeedResultDTO {
    private int requiredAmount;
    private double resultAmount;
    private long timeSpentInMs;
}
