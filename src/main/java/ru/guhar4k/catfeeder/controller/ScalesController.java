package ru.guhar4k.catfeeder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.guhar4k.catfeeder.model.dto.WeightDTO;
import ru.guhar4k.catfeeder.service.weight.WeightMeasurementService;

@RestController
@RequestMapping("/scales")
@RequiredArgsConstructor
public class ScalesController {
    private final WeightMeasurementService measurementService;

    @GetMapping("/tare")
    public ResponseEntity<Void> getWeight() {
        measurementService.tare();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/weight")
    public ResponseEntity<WeightDTO> getWeight(@RequestParam(required = false) Integer count, @RequestParam(required = false) Integer calibrationFactor) {
        return new ResponseEntity<>(measurementService.getWeight(count, calibrationFactor), HttpStatus.OK);
    }
}
