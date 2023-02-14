package ru.guhar4k.catfeeder.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.guhar4k.catfeeder.model.dto.StepperDto;
import ru.guhar4k.catfeeder.model.enumeration.Direction;

import java.util.List;

@RequestMapping("/stepper")
public interface StepperApi {

    @GetMapping("/list")
    @Schema(name = "Получение списка шаговых двигателей")
    ResponseEntity<List<StepperDto>> getAllSteppers();

    @GetMapping("/{id}/rotate")
    @Schema(name = "Вращение шаговым двигателей")
    ResponseEntity<Void> rotate(
            @PathVariable Integer id,
            @RequestParam Direction direction,
            @RequestParam int revolutions,
            @RequestParam boolean hold);
}
