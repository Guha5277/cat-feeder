package ru.guhar4k.catfeeder.service.motor.imlp;

import com.pi4j.io.gpio.*;
import lombok.Getter;
import ru.guhar4k.catfeeder.model.enumeration.Direction;

import static ru.guhar4k.catfeeder.utils.Utils.sleepMicro;

/**
 * Представление драйвера шагового двигателя
 */
@Getter
public class MotorDriver {
    public static final int RISE_TO_LOW_STEP_TIME = 100;
    protected final GpioPinDigitalOutput enablePin;
    protected final GpioPinDigitalOutput dirPin;
    protected final GpioPinDigitalOutput stepPin;

    protected final int microSteps;
    protected int sleepBetweenSteps;

    /**
     * Базовый конструктор инициализации драйвера шагового двигателя
     *
     * @param enablePinName     наименование GPIO отвечающего за включение/выключение ШД
     * @param dirPinName        наименование GPIO отвечающего за направление вращения ШД
     * @param stepPinName       наименование GPIO отвечающего за шаги ШД
     * @param microSteps        значение микрошагов установленных на драйвере, используется в расчётах определения полноты вращения
     * @param sleepBetweenSteps значение паузы между шагами (в миллисекундах), изменение этого параметра влияет на скорость вращения ШД
     */
    public MotorDriver(String enablePinName,
                       String dirPinName,
                       String stepPinName,
                       int microSteps,
                       int sleepBetweenSteps) {

        GpioController gpio = GpioFactory.getInstance();
        this.enablePin = gpio.provisionDigitalOutputPin(RaspiPin.getPinByName(enablePinName));
        this.dirPin = gpio.provisionDigitalOutputPin(RaspiPin.getPinByName(dirPinName));
        this.stepPin = gpio.provisionDigitalOutputPin(RaspiPin.getPinByName(stepPinName));
        this.microSteps = microSteps;
        this.sleepBetweenSteps = sleepBetweenSteps;
    }

    /**
     * Выполняет один шаг двигателя
     */
    public void step() {
        stepPin.high();
        sleepMicro(RISE_TO_LOW_STEP_TIME);
        stepPin.low();
        sleepMicro(sleepBetweenSteps);
    }

    /**
     * Выполняет удержание двигателя
     */
    public void hold() {
        enablePin.low();
    }

    /**
     * Освобождает двигатель от удержания
     */
    public void release() {
        enablePin.high();
    }

    public void setDirection(Direction direction) {
        dirPin.setState(direction.equals(Direction.CLOCKWISE) ? PinState.LOW : PinState.HIGH);
    }
}
