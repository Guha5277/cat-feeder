package ru.guhar4k.catfeeder.service;

import ru.guhar4k.catfeeder.model.dto.WeightDTO;

public interface WeightMeasurementService {

    /**
     * Метод измерения веса со стандартными значениями количества замеров и калибровочного значения
     *
     * @return значение веса в граммах
     */
    WeightDTO getWeight();

    /**
     * Метод измерения медианного значения веса с заданным количеством замеров (сэмплов)
     *
     * @param readsCount необходимое количество замеров для определения среднего значения
     */
    WeightDTO getWeight(int readsCount);


    /**
     * Обнуление значения веса
     */
    void tare();
}
