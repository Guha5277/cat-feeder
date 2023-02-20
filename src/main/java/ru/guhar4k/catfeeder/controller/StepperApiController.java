package ru.guhar4k.catfeeder.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.guhar4k.catfeeder.model.MotorServiceInfo;
import ru.guhar4k.catfeeder.model.dto.StepperDto;
import ru.guhar4k.catfeeder.model.enumeration.Direction;
import ru.guhar4k.catfeeder.service.MotorRegistryService;
import ru.guhar4k.catfeeder.service.motor.MotorService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stepper")
@Api(tags = "Управление шаговым двигателем")
public class StepperApiController {
    private final MotorRegistryService motorRegistryService;

    @GetMapping("/list")
    @ApiOperation("Получение списка сервисов шаговых двигателей")
    public ResponseEntity<List<StepperDto>> getAllSteppers() {
        List<MotorServiceInfo> motorServicesInfos = motorRegistryService.getAllMotorServicesInfos();
        List<StepperDto> result = motorServicesInfos.stream().map(si -> new StepperDto(si.getId(), si.getDescription())).collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}/rotate")
    @ApiOperation("Вращение шаговым двигателей")
    public ResponseEntity<Void> rotate(@PathVariable Integer id, @RequestParam Direction direction, @RequestParam int revolutions, @RequestParam boolean hold) {
        MotorService motorService = Optional.ofNullable(motorRegistryService.getMotorServiceById(id)).orElseThrow(() -> new RuntimeException("Мотор с указанным id не найден"));
        motorService.makeRevolution(direction, revolutions, hold);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
