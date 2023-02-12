package ru.guhar4k.catfeeder.service.feeder.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequiredArgsConstructor
public class FeederServiceImpl implements FeedService {
    public final static double INIT_REVOLUTIONS_COUNT = 0.5;
    private final MotorService motorService;
    private final WeightMeasurementService weightMeasurementService;

    @Override
    public FeedResultDTO feed(int requiredAmount) {
        log.info("Инициализирован процесс подачи корма");

        double previousAmount, resultAmount = 0;
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

//    @Override
//    public ResponseEntity<String> rotateForward(int count) {
//        boolean success = true;
//
//        try {
//            motorService.makeRevolution(Direction.CLOCKWISE, count, true);
//            log.info("Сделано {} оборотов", count);
//        } catch (MotorStuckException e) {
//            try {
//                log.info("Мотор застрял, вращаем в обратном направлении");
//                sleepSecond(1);
//                motorService.makeRevolution(Direction.COUNTERCLOCKWISE, count / 2, true);
//            } catch (MotorStuckException e2) {
//                success = false;
//                log.info("Мотор застрял в обратном направлении");
//            }
//        }
//
//        return new ResponseEntity<>(success ? "Операция провеедена успешно" : "Мотор заклинило в обоих направлениях", success ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @Override
//    public ResponseEntity<String> shakeTest(int shakes, int stepsForward, int stepsBackward) {
//        for (int i = 0; i < shakes; i++) {
//            motorService.shake(stepsForward, stepsBackward);
//        }
//        return new ResponseEntity<>("Готово", HttpStatus.OK);
//    }
}
