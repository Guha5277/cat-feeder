package ru.guhar4k.catfeeder.service.motor;

import ru.guhar4k.catfeeder.model.enumeration.Direction;

public interface MotorService {

    /**
     * Вращение с указанием количества оборотов
     *
     * @param direction        направление вращения
     * @param revolutionsCount необходимое количество оборотов
     * @param hold             флаг удержания шагового двигателя после завершения оборота
     */
    void makeRevolution(Direction direction, double revolutionsCount, boolean hold);

    /**
     * Параметризированная операция "вибрации" двигателем. Освобождение от заклинивания.
     * @param stepsForward количество шагов в прямом направлении
     * @param stepsBackward количество шагов в обратном направлении
     */
    void shake(int stepsForward, int stepsBackward);
}
