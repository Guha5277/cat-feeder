package ru.guhar4k.catfeeder.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.guhar4k.catfeeder.model.dto.CalibrationFactorResult;
import ru.guhar4k.catfeeder.service.imlp.ScalesCalibrationService;

//TODO добавить подробным описание методов API с порядком вызовов методов
@RestController
@RequestMapping("/calibrate")
@Api(tags = "Подбор калибровочного значения")
public class ScalesCalibratorApi {

    private final ScalesCalibrationService scalesCalibrationService;

    public ScalesCalibratorApi(ScalesCalibrationService scalesCalibrationService) {
        this.scalesCalibrationService = scalesCalibrationService;
    }

    @GetMapping("/byweight")
    @ApiOperation(value = "Калибровка с помощью передачи известного значения веса", notes = "")
    public ResponseEntity<CalibrationFactorResult> calibrateByWeight(@RequestParam double knownWeight) {
        return ResponseEntity.ok(scalesCalibrationService.calibrateByWeight(knownWeight));
    }


    @GetMapping("/testfactor")
    @ApiOperation(value = "Проверка калибровочного фактора", notes = "")
    public ResponseEntity<CalibrationFactorResult> testCalibrationFactor(@RequestParam int calibrationFactor) {
        return ResponseEntity.ok(scalesCalibrationService.testCalibrationFactor(calibrationFactor));
    }
}
