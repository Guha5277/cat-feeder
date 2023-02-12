package ru.guhar4k.catfeeder.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.guhar4k.catfeeder.model.dto.StepperDto;
import ru.guhar4k.catfeeder.model.enumeration.Direction;

@RequestMapping("/stepper")
@Schema(name = "Управление шаговым двигателем", description = "Контроль над шаговыми двигателями в системе - вращение, шаги, прочие функции")
public interface StepperApi {

    @GetMapping("/list")
    @Schema(name = "Получение списка шаговых двигателей")
    ResponseEntity<StepperDto> getAllSteppers();

    @GetMapping("/{id}/rotate")
    @Schema(name = "Вращение шаговым двигателей")
    ResponseEntity<Void> getWeight(
            @PathVariable Integer id,
            @RequestParam Direction direction,
            @RequestParam int revolutions,
            @RequestParam boolean hold);
}
