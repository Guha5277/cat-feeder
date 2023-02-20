package ru.guhar4k.catfeeder.service.weight.impl;

import com.pi4j.io.gpio.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.guhar4k.catfeeder.model.dto.WeightDTO;
import ru.guhar4k.catfeeder.model.enumeration.Gain;
import ru.guhar4k.catfeeder.service.weight.WeightMeasurementService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.guhar4k.catfeeder.utils.Utils.sleepMicro;

@Slf4j
@Service
public class HX711WeightMeasurementService implements WeightMeasurementService {
    public static final int CLOCK_PULSE_COUNT_FOR_READ_VALUE = 24;
    private final GpioPinDigitalInput dataPin;
    private final GpioPinDigitalOutput clockPin;
    private final Gain gain;
    private final int defaultReadsCount;
    private final int defaultCalibrationFactor;

    private double tareWeight;

    /**
     * Базовый конструктор
     * @param dataPinName номер gpio пина для считывания входных данных
     * @param clockPinName номер gpio пина для тактирования (управления передачей) данных
     * @param gain коэффициент усиления (см. datasheet к HX711)
     * @param defaultReadsCount количество совершаемых замеров для получения итогового среднего значения веса
     * @param defaultCalibrationFactor калибровочное значение используемое в расчётах итогового веса (подбирается опытным путём, уникально для каждого тензодатчика)
     */
    public HX711WeightMeasurementService(@Value("${catfeeder.hardware.hx711.pins.DT}") String dataPinName,
                                         @Value("${catfeeder.hardware.hx711.pins.CLCK}") String clockPinName,
                                         @Value("${catfeeder.hardware.hx711.gain}") Gain gain,
                                         @Value("${catfeeder.hardware.hx711.defaultReadsCount}") int defaultReadsCount,
                                         @Value("${catfeeder.hardware.hx711.defaultCalibrationFactor}") int defaultCalibrationFactor) {

        GpioController gpio = GpioFactory.getInstance();
        dataPin = gpio.provisionDigitalInputPin(RaspiPin.getPinByName(dataPinName));
        clockPin = gpio.provisionDigitalOutputPin(RaspiPin.getPinByName(clockPinName), PinState.HIGH);
        this.gain = gain;
        this.defaultReadsCount = defaultReadsCount;
        this.defaultCalibrationFactor = defaultCalibrationFactor;
    }

    @Override
    public WeightDTO getWeight() {
        WeightDTO result = getWeight(defaultReadsCount, defaultCalibrationFactor);
        log.info("Результат измерения веса: {}", result);
        return result;
    }

    @Override
    public WeightDTO getWeight(Integer measuresCount, Integer calibrationFactor) {
        return measurementWeight(measuresCount == null ? defaultReadsCount : measuresCount,
                calibrationFactor == null ? defaultCalibrationFactor : calibrationFactor);
    }

    @Override
    public void tare() {
        int maxTareAccurateCount = 10;

        double result;
        int tareAccurateCount = 0;

        do {
            log.info("Обнуление тары");

            tareWeight = readValue(defaultReadsCount);
            WeightDTO weight = getWeight();
            result = weight.getWeightResult();
            tareAccurateCount++;

            if (tareAccurateCount > maxTareAccurateCount) {
                log.error("Ошибка обнуления тары в пределах допустимого диапазона");
                break;
            }
        } while (result > 0.3 || result < -0.3);
        log.info("Результат обнуления тары: {}", result);
    }

    private WeightDTO measurementWeight(int measuresCount, int calibrationFactor) {
        double value = readValue(measuresCount);
        double weight = (value - tareWeight) / calibrationFactor;

        return new WeightDTO(weight, measuresCount, calibrationFactor, tareWeight / calibrationFactor);
    }

    private double readValue(int readsCount) {
        List<Long> reads = new ArrayList<>(readsCount);

        for (int i = 0; i < readsCount; i++) {
            reads.add(measure());
        }

        log.info(String.format("Прочитанные значения[%d]: ", reads.size()) + reads);

        List<Long> distinctSortedReads = reads.stream().distinct().sorted().collect(Collectors.toList());
        log.info(String.format("Уникальные и отсортированные[%d]: ", distinctSortedReads.size()) + distinctSortedReads);

        Long referenceValue = distinctSortedReads.size() > 1 ? distinctSortedReads.get(distinctSortedReads.size() / 2) : distinctSortedReads.get(0);

        double averageValue = distinctSortedReads.stream()
                .filter(v -> Math.abs(referenceValue - v) < 100)
                .mapToDouble(aLong -> aLong)
                .average()
                .getAsDouble();

        log.info("Итоговое значение веса: " + averageValue + "\n");

        return averageValue;
    }

    private long measure() {
        wakeUpAndWaitModuleForReady();
        return readRawBitsValue() ^ 0x800000;
    }

    private void wakeUpAndWaitModuleForReady() {
        clockPin.low();

        //FIXME добавить таймаут обращения к модулю, на случай обрыва связи и вывода его из строя
        while (!moduleReady()) {
            sleepMicro(1);
        }
    }

    private boolean moduleReady() {
        return dataPin.isLow();
    }

    private long readRawBitsValue() {
        long result = 0;

        for (int i = 0; i < CLOCK_PULSE_COUNT_FOR_READ_VALUE; i++) {
            clockPin.high();
            sleepMicro(1);

            result = result << 1;

            clockPin.low();
            sleepMicro(1);

            if (dataPin.isHigh()) {
                result++;
            }
        }

        setGainForNextMeasure();
        return result;
    }

    private void setGainForNextMeasure() {
        switch (gain) {
            case GAIN_64:
                clock();
            case GAIN_32:
                clock();
            case GAIN_128:
                clock();
        }
    }

    private void clock() {
        clockPin.high();
        sleepMicro(1);
        clockPin.low();
        sleepMicro(1);
    }
}
