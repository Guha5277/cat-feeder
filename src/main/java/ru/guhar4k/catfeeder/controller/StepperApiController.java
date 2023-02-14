package ru.guhar4k.catfeeder.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
@Schema(name = "Управление шаговым двигателем", description = "Контроль над шаговыми двигателями в системе - вращение, шаги, прочие функции")
public class StepperApiController implements StepperApi {
    private final MotorRegistryService motorRegistryService;

    @Override
    public ResponseEntity<List<StepperDto>> getAllSteppers() {
        List<MotorServiceInfo> motorServicesInfos = motorRegistryService.getAllMotorServicesInfos();
        List<StepperDto> result = motorServicesInfos.stream().map(si -> new StepperDto(si.getId(), si.getDescription())).collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> rotate(@PathVariable Integer id, @RequestParam Direction direction, @RequestParam int revolutions, @RequestParam boolean hold) {
        MotorService motorService = Optional.ofNullable(motorRegistryService.getMotorServiceById(id)).orElseThrow(() -> new RuntimeException("Мотор с указанным id не найден"));
        motorService.makeRevolution(direction, revolutions, hold);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
