package ru.guhar4k.catfeeder.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "WeightDTO", description = "Результат измерения веса")
public class WeightDTO {

    @Schema(name = "weightResult", description = "Значение веса в граммах")
    private double weightResult;

    @Schema(name = "measuresCount", description = "Количество итераций измерения")
    private int measuresCount;
}
