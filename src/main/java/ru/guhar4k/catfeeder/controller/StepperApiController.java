package ru.guhar4k.catfeeder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.guhar4k.catfeeder.model.dto.StepperDto;
import ru.guhar4k.catfeeder.model.enumeration.Direction;
import ru.guhar4k.catfeeder.service.motor.MotorService;

@RestController
@RequiredArgsConstructor
public class StepperApiController implements StepperApi {
    private final MotorService motorService;

    @Override
    public ResponseEntity<StepperDto> getAllSteppers() {
        return null;
    }

    @Override
    public ResponseEntity<Void> getWeight(@PathVariable Integer id, @RequestParam Direction direction, @RequestParam int revolutions, @RequestParam boolean hold) {
        motorService.makeRevolution(direction, revolutions, hold);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
