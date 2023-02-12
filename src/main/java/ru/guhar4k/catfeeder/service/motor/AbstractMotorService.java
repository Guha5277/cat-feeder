package ru.guhar4k.catfeeder.service.motor;

import com.pi4j.io.gpio.*;
import lombok.Setter;
import ru.guhar4k.catfeeder.model.enumeration.Direction;

import static ru.guhar4k.catfeeder.utils.Utils.sleepMicro;

@Setter
public abstract class AbstractMotorService implements MotorService {
    public static final int RISE_TO_LOW_STEP_TIME = 100;
    protected final GpioPinDigitalOutput enablePin;
    protected final GpioPinDigitalOutput dirPin;
    protected final GpioPinDigitalOutput stepPin;
    protected final int stepsPerRevolution;
    protected final int microSteps;
    protected int sleepBetweenSteps;

    /**
     * Базовый конструктор инициации шагового двигателя
     * @param enablePinName наименование GPIO отвечающего за включение/выключение ШД
     * @param dirPinName наименование GPIO отвечающего за направление вращения ШД
     * @param stepPinName наименование GPIO отвечающего за шаги ШД
     * @param stepsPerRevolution количество шагов на полный оборот (по спецификации ШД)
     * @param microSteps значение микрошагов установленных на драйвере
     * @param sleepBetweenSteps значение паузы между шагами (в миллисекундах), изменение этого параметра влияет на скорость вращения ШД
     */
    public AbstractMotorService(String enablePinName,
                                String dirPinName,
                                String stepPinName,
                                int stepsPerRevolution,
                                int microSteps,
                                int sleepBetweenSteps) {

        GpioController gpio = GpioFactory.getInstance();
        this.enablePin = gpio.provisionDigitalOutputPin(RaspiPin.getPinByName(enablePinName));
        this.dirPin = gpio.provisionDigitalOutputPin(RaspiPin.getPinByName(dirPinName));
        this.stepPin = gpio.provisionDigitalOutputPin(RaspiPin.getPinByName(stepPinName));
        this.stepsPerRevolution = stepsPerRevolution;
        this.microSteps = microSteps;
        this.sleepBetweenSteps = sleepBetweenSteps;
    }

    @Override
    public void makeRevolution(Direction direction, double revolutionsCount, boolean hold) {
        makeSteps(direction, (long) (revolutionsCount * stepsPerRevolution * microSteps), hold);
    }

    protected void singleStep(Direction direction, boolean hold) {
        makeSteps(direction, 1, hold);
    }

    protected void makeSteps(Direction direction, long steps, boolean hold) {
        enableAndSetDirection(direction);

        for (int i = 0; i < steps; i++) {
            step();
        }

        disableMotorIfRequired(hold);
    }

    protected void enableAndSetDirection(Direction direction) {
        enablePin.low();
        setDirection(direction);
    }

    protected void setDirection(Direction direction) {
        dirPin.setState(direction.equals(Direction.CLOCKWISE) ? PinState.LOW : PinState.HIGH);
    }

    protected void disableMotorIfRequired(boolean hold) {
        if (!hold) enablePin.high();
    }

    protected void step() {
        stepPin.high();
        sleepMicro(RISE_TO_LOW_STEP_TIME);
        stepPin.low();
        sleepMicro(sleepBetweenSteps);
    }
}
