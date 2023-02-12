package ru.guhar4k.catfeeder.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.guhar4k.catfeeder.service.motor.MotorService;
import ru.guhar4k.catfeeder.service.motor.imlp.StuckDetectMotorService;

@Configuration
public class MotorConfig {

    @Bean
    public MotorService getMotorService(@Value("${catfeeder.hardware.encoder.pin}") String encoderPinName,
                                        @Value("${catfeeder.hardware.feederStepper.pins.EN}") String enablePinName,
                                        @Value("${catfeeder.hardware.feederStepper.pins.DIR}") String dirPinName,
                                        @Value("${catfeeder.hardware.feederStepper.pins.STEP}") String stepPinName,
                                        @Value("${catfeeder.hardware.feederStepper.stepsPerRevolution}") int stepsPerRevolution,
                                        @Value("${catfeeder.hardware.feederStepper.microSteps}") int microSteps,
                                        @Value("${catfeeder.hardware.feederStepper.sleepBetweenSteps}") int sleepBetweenSteps) {

        return new StuckDetectMotorService(encoderPinName, enablePinName, dirPinName, stepPinName, stepsPerRevolution, microSteps, sleepBetweenSteps);
    }
}
