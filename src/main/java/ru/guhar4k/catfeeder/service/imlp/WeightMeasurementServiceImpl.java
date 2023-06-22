package ru.guhar4k.catfeeder.service.imlp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.guhar4k.catfeeder.configuration.CatFeederProperties;
import ru.guhar4k.catfeeder.model.dto.WeightDTO;
import ru.guhar4k.catfeeder.service.WeightMeasurementService;
import ru.guhar4k.gpio.core.hardware.ScalesController;

@Slf4j
@Service
public class WeightMeasurementServiceImpl implements WeightMeasurementService {

    private final ScalesController scalesController;
    private int averageWeightMeasures;

    public WeightMeasurementServiceImpl(ScalesController scalesController) {
        this.scalesController = scalesController;
    }

    @Override
    public WeightDTO getWeight() {
        return new WeightDTO(readMedian(averageWeightMeasures), averageWeightMeasures);
    }

    @Override
    public WeightDTO getWeight(int readsCount) {
        return new WeightDTO(readMedian(readsCount), readsCount);
    }

    @Override
    public void tare() {
        scalesController.tare();
    }


    private double readMedian(int readsCount) {
        return scalesController.weightMedian(readsCount);
    }

    public void setAverageWeightMeasures(int averageWeightMeasures) {
        this.averageWeightMeasures = averageWeightMeasures;
    }

    @Autowired
    public void setProperties(CatFeederProperties properties) {
        setAverageWeightMeasures(properties.getAverageWeightMeasures());
    }
}
