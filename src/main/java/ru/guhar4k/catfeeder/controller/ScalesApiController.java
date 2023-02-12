package ru.guhar4k.catfeeder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.guhar4k.catfeeder.model.dto.WeightDTO;
import ru.guhar4k.catfeeder.service.weight.WeightMeasurementService;

@RestController
@RequiredArgsConstructor
public class ScalesApiController implements ScalesApi {
    private final WeightMeasurementService measurementService;

    @Override
    public ResponseEntity<Void> tare() {
        measurementService.tare();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<WeightDTO> tare(@RequestParam(required = false) Integer count, @RequestParam(required = false) Integer calibrationFactor) {
        return new ResponseEntity<>(measurementService.getWeight(count, calibrationFactor), HttpStatus.OK);
    }
}
