package ru.guhar4k.catfeeder.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class WeightDTO {
    private double weightResult;
    private int measuresCount;
    private int scaleFactor;
    private double tareWeight;
}
