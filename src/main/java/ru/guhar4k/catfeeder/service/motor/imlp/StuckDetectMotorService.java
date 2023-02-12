package ru.guhar4k.catfeeder.service.motor.imlp;

import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.RaspiPin;
import lombok.extern.slf4j.Slf4j;
import ru.guhar4k.catfeeder.model.enumeration.Direction;
import ru.guhar4k.catfeeder.model.exception.MotorStuckException;
import ru.guhar4k.catfeeder.service.motor.AbstractMotorService;

import static ru.guhar4k.catfeeder.utils.Utils.sleepMicro;

/**
 * Сервис работы с шаговым двигателем с поддержкой обратной связи
 */
@Slf4j
public class StuckDetectMotorService extends AbstractMotorService {
    private final static int AVERAGE_EDGE_TO_EDGE_STEPS = 13;
    private final static int STEP_LOSS_TOLERANCE = 3;
    private final static int SLEEP_BETWEEN_STEPS_VALUE = 150;

    private final GpioPinDigitalInput encoderPin;

    public StuckDetectMotorService(String encoderPinName, String enablePinName, String dirPinName, String stepPinName, int stepsPerRevolution, int microSteps, int sleepBetweenSteps) {
        super(enablePinName, dirPinName, stepPinName, stepsPerRevolution, microSteps, sleepBetweenSteps);
        this.encoderPin = GpioFactory.getInstance().provisionDigitalInputPin(RaspiPin.getPinByName(encoderPinName));
    }

    @Override
    protected void makeSteps(Direction direction, long steps, boolean hold) throws MotorStuckException {
        enableAndSetDirection(direction);

        boolean currentEncoderState, previousEncoderState = encoderPin.isHigh();
        int encoderHighStatesCount = 0, stepsBetweenEdges = 0;

        for (int i = 0; i < steps; i++) {
            step();

            if (steps == 1) {
                return;
            }

            sleepMicro(SLEEP_BETWEEN_STEPS_VALUE);
            stepsBetweenEdges++;

            if (stepsBetweenEdges - AVERAGE_EDGE_TO_EDGE_STEPS > STEP_LOSS_TOLERANCE) {
                log.info("Двигатель заклинило в направлении {}!", direction);
                disableMotorIfRequired(hold);
                throw new MotorStuckException(String.format("Двигатель заклининло в направлении %s", direction), i);
            }

            currentEncoderState = encoderPin.isHigh();

            if (!previousEncoderState && currentEncoderState) {
//                if (encoderHighStatesCount > 1 && stepsBetweenEdges < AVERAGE_EDGE_TO_EDGE_STEPS - STEP_LOSS_TOLERANCE) {
//                    log.info("Двигатель заклинило в направлении {}!", direction);
//                    throw new MotorStuckException(String.format("Двигатель заклининло в направлении %s", direction), i);
//                }

                encoderHighStatesCount++;

                log.info("Точка {}, шагов с предыдущей точки: {}", encoderHighStatesCount, stepsBetweenEdges);
                stepsBetweenEdges = 0;
            }

            previousEncoderState = currentEncoderState;
        }

        disableMotorIfRequired(hold);
    }

    @Override
    public void shake(int stepsForward, int stepsBackward) {
        for (int i = 0; i < stepsForward; i++) {
            singleStep(Direction.CLOCKWISE, true);
        }

        for (int i = 0; i < stepsBackward; i++) {
            singleStep(Direction.COUNTERCLOCKWISE, true);
        }
    }
}
