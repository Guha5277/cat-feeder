package ru.guhar4k.catfeeder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.guhar4k.catfeeder.model.enumeration.Direction;
import ru.guhar4k.catfeeder.service.motor.MotorService;

@RestController
@RequestMapping("/feeder-stepper")
@RequiredArgsConstructor
public class FeederStepperApiController {
    private final MotorService motorService;

    @GetMapping("/rotate")
    public ResponseEntity<Void> getWeight(@RequestParam Direction direction, @RequestParam int revolutions, @RequestParam boolean hold) {
        motorService.makeRevolution(direction, revolutions, hold);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
