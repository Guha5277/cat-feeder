package ru.guhar4k.catfeeder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.guhar4k.catfeeder.model.dto.FeedResultDTO;
import ru.guhar4k.catfeeder.service.feeder.FeedService;

@RestController
@RequiredArgsConstructor
public class FeederApiController implements FeederApi {
    private final FeedService feedService;

    @Override
    public ResponseEntity<FeedResultDTO> feed(@RequestParam int amount) {
        FeedResultDTO result = feedService.feed(amount);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
