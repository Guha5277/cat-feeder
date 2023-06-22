package ru.guhar4k.catfeeder.service.imlp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.guhar4k.catfeeder.model.dto.FeedResultDTO;
import ru.guhar4k.catfeeder.service.FeedService;
import ru.guhar4k.catfeeder.service.MotorService;
import ru.guhar4k.catfeeder.service.WeightMeasurementService;
import ru.guhar4k.gpio.core.property.Direction;

@Slf4j
@Service
public class FeederServiceImpl implements FeedService {
    public final static double INIT_REVOLUTIONS_COUNT = 0.5;
    private final MotorService motorService;
    private final WeightMeasurementService weightMeasurementService;

    @Autowired
    public FeederServiceImpl(MotorService motorService, WeightMeasurementService weightMeasurementService) {
        this.motorService = motorService;
        this.weightMeasurementService = weightMeasurementService;
    }

    @Override
    public FeedResultDTO feed(int requiredAmount) {
        log.info("Инициализирован процесс подачи корма, требуемый объём: {}", requiredAmount);

        double resultAmount;
        FeedResultDTO result = new FeedResultDTO();
        result.setRequiredAmount(requiredAmount);
        long start = System.currentTimeMillis();
        double revolutionsCount = INIT_REVOLUTIONS_COUNT;

        weightMeasurementService.tare();

        while (true) {
            resultAmount = weightMeasurementService.getWeight().getWeightResult();

            if (resultAmount >= requiredAmount) {
                break;
            }

            if (resultAmount > 0) {
                double requiredToResultPercentageRatio = resultAmount / requiredAmount;
                log.info("Выполнено {}%", (int) (requiredToResultPercentageRatio * 100));
                revolutionsCount = Math.max(0.1, INIT_REVOLUTIONS_COUNT - (INIT_REVOLUTIONS_COUNT * requiredToResultPercentageRatio));
                log.info("Новое количество revolutionsCount {}", revolutionsCount);
            }

            motorService.makeRevolution(Direction.CLOCKWISE, revolutionsCount, true);
            log.info("Сделано {} оборотов, результат веса {}", revolutionsCount, resultAmount);
        }

        result.setTimeSpentInMs(System.currentTimeMillis() - start);
        result.setResultAmount(resultAmount);
        return result;
    }
}
