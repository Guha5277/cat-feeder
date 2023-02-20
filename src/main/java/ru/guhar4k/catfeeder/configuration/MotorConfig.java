package ru.guhar4k.catfeeder.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.guhar4k.catfeeder.service.MotorRegistryService;
import ru.guhar4k.catfeeder.service.motor.imlp.MotorDriver;
import ru.guhar4k.catfeeder.service.motor.MotorService;
import ru.guhar4k.catfeeder.service.motor.imlp.SimpleMotorService;
import ru.guhar4k.catfeeder.service.motor.imlp.StuckDetectMotorService;

@Configuration
@RequiredArgsConstructor
public class MotorConfig {

    private final MotorRegistryService motorRegistryService;

    @Bean(name = "feederMotorDriver")
    public MotorDriver feederMotoDriver(@Value("${catfeeder.hardware.feederStepper.pins.EN}") String enablePinName,
                                        @Value("${catfeeder.hardware.feederStepper.pins.DIR}") String dirPinName,
                                        @Value("${catfeeder.hardware.feederStepper.pins.STEP}") String stepPinName,
                                        @Value("${catfeeder.hardware.feederStepper.microSteps}") int microSteps,
                                        @Value("${catfeeder.hardware.feederStepper.sleepBetweenSteps}") int sleepBetweenSteps) {
        return new MotorDriver(enablePinName, dirPinName, stepPinName, microSteps, sleepBetweenSteps);
    }


    @Bean("stuckDetectMotorService")
    public MotorService stuckDetectMotorService(@Autowired @Qualifier("feederMotorDriver") MotorDriver motorDriver,
                                                @Value("${catfeeder.hardware.encoder.pin}") String encoderPinName,
                                                @Value("${catfeeder.hardware.feederStepper.stepsPerRevolution}") int stepsPerRevolution) {

        var motorService = new StuckDetectMotorService("Сервис с обратной связью энкодера", motorDriver, stepsPerRevolution, encoderPinName);
        motorRegistryService.registerMotorService(motorService);
        return motorService;
    }

    @Bean
    public SimpleMotorService simpleMotorService(@Autowired @Qualifier("feederMotorDriver") MotorDriver motorDriver,
                                                 @Value("${catfeeder.hardware.feederStepper.stepsPerRevolution}") int stepsPerRevolution) {

        var motorService = new SimpleMotorService("Простой сервис управления ШД", motorDriver, stepsPerRevolution);
        motorRegistryService.registerMotorService(motorService);
        return motorService;
    }
}
