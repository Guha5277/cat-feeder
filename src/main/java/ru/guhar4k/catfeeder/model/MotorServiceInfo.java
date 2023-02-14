package ru.guhar4k.catfeeder.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.guhar4k.catfeeder.service.motor.MotorService;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MotorServiceInfo {

    private Integer id;
    private MotorService motorService;
    private String description;
}
