package ru.guhar4k.catfeeder.service.imlp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.guhar4k.catfeeder.service.MotorService;
import ru.guhar4k.gpio.core.hardware.MotorDriver;
import ru.guhar4k.gpio.core.property.Direction;

@Service
@RequiredArgsConstructor
public class MotorServiceImpl implements MotorService {

    private final MotorDriver motorDriver;
    private int stepsPerRevolution;

    @Override
    public void makeRevolution(Direction direction, double revolutionsCount, boolean hold) {
        motorDriver.setDirection(direction);
        for (int i = 0; i < stepsPerRevolution; i++) {
            motorDriver.step();
        }

        if (hold) {
            motorDriver.hold();
        }
    }

    @Override
    public void shake(int stepsForward, int stepsBackward) {
        motorDriver.setDirection(Direction.CLOCKWISE);
        for (int i = 0; i < stepsForward; i++) {
            motorDriver.step();
        }

        motorDriver.setDirection(Direction.COUNTERCLOCKWISE);
        for (int i = 0; i < stepsBackward; i++) {
            motorDriver.step();
        }
    }
}
