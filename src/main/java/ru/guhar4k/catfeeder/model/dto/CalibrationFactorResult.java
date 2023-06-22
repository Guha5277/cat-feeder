package ru.guhar4k.catfeeder.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CalibrationFactorResult {
    private Integer calibrationFactor;
    private Double weightResult;
}
