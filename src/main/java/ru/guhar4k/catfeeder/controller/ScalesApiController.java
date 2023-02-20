package ru.guhar4k.catfeeder.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.guhar4k.catfeeder.model.dto.WeightDTO;
import ru.guhar4k.catfeeder.service.weight.WeightMeasurementService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/scales")
@Api(tags = "Измерение веса")
public class ScalesApiController {
    private final WeightMeasurementService measurementService;

    @GetMapping("/tare")
    @ApiOperation("Обнуление текущего веса")
    public ResponseEntity<Void> weight() {
        measurementService.tare();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/weight")
    @ApiOperation("Получение значения веса")
    public ResponseEntity<WeightDTO> weight(@Schema(name = "count", description = "(Опционально) количество итераций (больше - точнее)")
                                            @RequestParam(required = false) Integer count,

                                            @Schema(name = "calibrationFactor", description = "(Опционально) калибровочное значение уникальное для модуля измерения веса")
                                            @RequestParam(required = false) Integer calibrationFactor) {
        return new ResponseEntity<>(measurementService.getWeight(count, calibrationFactor), HttpStatus.OK);
    }
}
