package ru.guhar4k.catfeeder.service.weight;

import ru.guhar4k.catfeeder.model.dto.WeightDTO;

public interface WeightMeasurementService {

    /**
     * Метод измерения веса со стандартными значениями количества замеров и калибровочного значения
     * @return значение веса в граммах
     */
    WeightDTO getWeight();

    /**
     * Метод измерения веса
     * @param measuresCount количество замеров значения веса
     * @param calibrationFactor калибровочное значение, уникальное для каждого датчика
     * @return DTO значения веса и параметров замера
     */
    WeightDTO getWeight(Integer measuresCount, Integer calibrationFactor);

    /**
     * Обнуление значения веса
     */
    void tare();
}
