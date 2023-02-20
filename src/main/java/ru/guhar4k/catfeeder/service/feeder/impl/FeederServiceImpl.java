package ru.guhar4k.catfeeder.service.feeder.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.guhar4k.catfeeder.model.dto.FeedResultDTO;
import ru.guhar4k.catfeeder.model.enumeration.Direction;
import ru.guhar4k.catfeeder.model.exception.MotorStuckException;
import ru.guhar4k.catfeeder.service.feeder.FeedService;
import ru.guhar4k.catfeeder.service.motor.MotorService;
import ru.guhar4k.catfeeder.service.weight.WeightMeasurementService;

import static ru.guhar4k.catfeeder.utils.Utils.sleepSecond;

@Slf4j
@Service

public class FeederServiceImpl implements FeedService {
    public final static double INIT_REVOLUTIONS_COUNT = 0.5;
    private final MotorService motorService;
    private final WeightMeasurementService weightMeasurementService;

    @Autowired
    public FeederServiceImpl(@Qualifier("stuckDetectMotorService") MotorService motorService, WeightMeasurementService weightMeasurementService) {
        this.motorService = motorService;
        this.weightMeasurementService = weightMeasurementService;
    }

    @Override
    public FeedResultDTO feed(int requiredAmount) {
        log.info("Инициализирован процесс подачи корма");

        double resultAmount = 0;
        FeedResultDTO result = new FeedResultDTO();
        result.setRequiredAmount(requiredAmount);
        long start = System.currentTimeMillis();
        double revolutionsCount = INIT_REVOLUTIONS_COUNT;

        weightMeasurementService.tare();

        int sequenceFailsCount = 0;

        while (true) {
            try {
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
                sequenceFailsCount = 0;
            } catch (MotorStuckException e) {
                try {
                    log.info("Мотор застрял, вращаем в обратном направлении");
                    motorService.makeRevolution(Direction.COUNTERCLOCKWISE, revolutionsCount / 2, true);
                } catch (MotorStuckException e2) {
                    log.info("Мотор застрял в обратном направлении. Количество застреваний подряд: {}", ++sequenceFailsCount);

                    for (int i = 0; i < sequenceFailsCount; i++) {
                        motorService.shake(100, 80);
                    }

                    sleepSecond(1);
                }
            }
        }

        result.setTimeSpentInMs(System.currentTimeMillis() - start);
        result.setResultAmount(resultAmount);
        return result;
    }
}
