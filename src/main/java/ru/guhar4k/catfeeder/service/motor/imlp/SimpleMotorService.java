package ru.guhar4k.catfeeder.service.motor.imlp;

import ru.guhar4k.catfeeder.service.motor.AbstractMotorService;

public class SimpleMotorService extends AbstractMotorService {

    /**
     * Простая реализация сервиса управления шаговым двигателем
     *
     * @param name               название (назначение) сервиса управления шаговым двигателем
     * @param motorDriver        драйвер управления шаговым двигателем
     * @param stepsPerRevolution количество шагов на полный оборот (по спецификации ШД)
     */
    public SimpleMotorService(String name, MotorDriver motorDriver, int stepsPerRevolution) {
        super(name, motorDriver, stepsPerRevolution);
    }
}
