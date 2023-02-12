package ru.guhar4k.catfeeder.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "FeedResultDTO", description = "Информация о завершении процедуры выдачи корма")
public class FeedResultDTO {

    @Schema(name = "requiredAmount", description = "Запрашиваемое количество выдачи корма")
    private int requiredAmount;

    @Schema(name = "resultAmount", description = "Итоговое количество выдачи корма")
    private double resultAmount;

    @Schema(name = "timeSpentInMs", description = "Общее затраченное время на выдачу корма")
    private long timeSpentInMs;
}
