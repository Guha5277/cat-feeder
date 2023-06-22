package ru.guhar4k.catfeeder.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.guhar4k.gpio.core.hardware.MotorDriver;
import ru.guhar4k.gpio.core.hardware.ScalesController;
import ru.guhar4k.gpio.hardware.module.provider.ModuleProvider;

@Configuration
public class ModulesProviderConfiguration {

    @Bean(name = "${hardware.modules.scales[0].scales-name}")
    public ScalesController getScalesModuleForFeeder(@Value("${hardware.modules.scales[0].scales-name}") String moduleName,
                                                     ModuleProvider<ScalesController> scalesControllerModuleProvider) {
        return scalesControllerModuleProvider.getModuleByName(moduleName);
    }

    @Bean(name = "(${hardware.modules.motor-drivers[0].driver-name}")
    public MotorDriver getMotorDriverForFeeder(@Value("${hardware.modules.motor-drivers[0].driver-name}") String driverName,
                                                     ModuleProvider<MotorDriver> motorDriverModuleProvider) {
        return motorDriverModuleProvider.getModuleByName(driverName);
    }
}
