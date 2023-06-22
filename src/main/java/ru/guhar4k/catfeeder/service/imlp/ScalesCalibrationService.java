package ru.guhar4k.catfeeder.service.imlp;

import org.springframework.stereotype.Service;
import ru.guhar4k.catfeeder.model.dto.CalibrationFactorResult;
import ru.guhar4k.catfeeder.model.dto.WeightDTO;
import ru.guhar4k.catfeeder.service.WeightMeasurementService;
import ru.guhar4k.gpio.starter.properties.ScalesControllerProperties;

import static ru.guhar4k.catfeeder.utils.Utils.round;

@Service
public class ScalesCalibrationService {

    private final WeightMeasurementService weightMeasurementService;
    private int calibrationFactor;

    public ScalesCalibrationService(WeightMeasurementService weightMeasurementService, ScalesControllerProperties scalesControllerProperties) {
        this.weightMeasurementService = weightMeasurementService;
        this.calibrationFactor = scalesControllerProperties.getCalibrationFactor();
    }

    public CalibrationFactorResult calibrateByWeight(double knownWeight) {
        WeightDTO weight = weightMeasurementService.getWeight(1);
        double rawValue = weight.getWeightResult() * this.calibrationFactor;
        int newCalibrationFactor = (int) (rawValue / knownWeight);
        return new CalibrationFactorResult(newCalibrationFactor, round(knownWeight, 2));
    }

    public CalibrationFactorResult testCalibrationFactor(int calibrationFactor) {
        WeightDTO weight = weightMeasurementService.getWeight(1);
        double rawValue = weight.getWeightResult() * this.calibrationFactor;
        double valueWithNewCalibrationFactor = rawValue / calibrationFactor;
        return new CalibrationFactorResult(calibrationFactor, round(valueWithNewCalibrationFactor, 2));
    }
}
