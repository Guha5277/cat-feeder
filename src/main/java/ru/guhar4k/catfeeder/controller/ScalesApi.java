package ru.guhar4k.catfeeder.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.guhar4k.catfeeder.model.dto.WeightDTO;

@RequestMapping("/scales")
@Schema(name = "Измерение веса", description = "API работы с модулем весов - измерение веса, обнуление тары и т.д.")
public interface ScalesApi {

    @Schema(name = "Обнуление текущего веса")
    @GetMapping("/tare")
    ResponseEntity<Void> tare();

    @GetMapping("/weight")
    @Schema(name = "Получение значения веса")
    ResponseEntity<WeightDTO> tare(
            @Schema(name = "count", description = "(Опционально) количество итераций (больше - точнее)")
            @RequestParam(required = false) Integer count,

            @Schema(name = "calibrationFactor", description = "(Опционально) калибровочное значение уникальное для модуля измерения веса")
            @RequestParam(required = false) Integer calibrationFactor);
}
