package ru.guhar4k.catfeeder.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(name = "StepperDto", description = "Информация о шаговом двигателе")
public class StepperDto {

    @Schema(name = "id", description = "Идентификатор шагового двигателя")
    private Integer id;

    @Schema(name = "description", description = "Описание назначения шагового двигателя")
    private String description;
}
