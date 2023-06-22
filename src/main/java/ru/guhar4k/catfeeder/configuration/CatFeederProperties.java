package ru.guhar4k.catfeeder.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "catfeeder")
public class CatFeederProperties {

    /**
     * Количество измерений веса для получения среднего значения
     */
    private Integer averageWeightMeasures = 3;
}
