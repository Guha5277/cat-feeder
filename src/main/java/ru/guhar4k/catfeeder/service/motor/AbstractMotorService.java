package ru.guhar4k.catfeeder.service.motor;

import lombok.Getter;
import ru.guhar4k.catfeeder.model.enumeration.Direction;
import ru.guhar4k.catfeeder.service.motor.imlp.MotorDriver;

/**
 * Базовое представление сервиса управлением шаговым двигателем
 */
@Getter
public abstract class AbstractMotorService implements MotorService {
    private final String name;
    private final MotorDriver motorDriver;
    protected final int stepsPerRevolution;

    /**
     * Базовый конструктор всех сервисов шагового двигателя
     *
     * @param name               название (назначение) сервиса управления шаговым двигателем
     * @param motorDriver        драйвер управления шаговым двигателем
     * @param stepsPerRevolution количество шагов на полный оборот (по спецификации ШД)
     */
    public AbstractMotorService(String name, MotorDriver motorDriver, int stepsPerRevolution) {
        this.name = name;
        this.motorDriver = motorDriver;
        this.stepsPerRevolution = stepsPerRevolution;
    }

    @Override
    public void makeRevolution(Direction direction, double revolutionsCount, boolean hold) {
        makeSteps(direction, (long) (revolutionsCount * stepsPerRevolution * motorDriver.getMicroSteps()), hold);
    }

    protected void singleStep(Direction direction, boolean hold) {
        makeSteps(direction, 1, hold);
    }

    protected void makeSteps(Direction direction, long steps, boolean hold) {
        holdAndSetDirection(direction);

        for (int i = 0; i < steps; i++) {
            step();
        }

        disableMotorIfRequired(hold);
    }

    protected void holdAndSetDirection(Direction direction) {
        motorDriver.hold();
        motorDriver.setDirection(direction);
    }

    protected void setDirection(Direction direction) {
        motorDriver.setDirection(direction);
    }

    protected void disableMotorIfRequired(boolean hold) {
        if (!hold) motorDriver.release();
    }

    protected void step() {
        motorDriver.step();
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
