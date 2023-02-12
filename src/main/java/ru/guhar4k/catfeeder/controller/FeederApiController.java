package ru.guhar4k.catfeeder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.guhar4k.catfeeder.model.dto.FeedResultDTO;
import ru.guhar4k.catfeeder.service.feeder.FeedService;


@RestController
@RequestMapping("/feeder")
@RequiredArgsConstructor
public class FeederApiController {
    private final FeedService feedService;

    @GetMapping
    public ResponseEntity<FeedResultDTO> feed(@RequestParam int amount) {
        FeedResultDTO result = feedService.feed(amount);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

//    @GetMapping("/rotate")
//    public ResponseEntity<String> rotate(@RequestParam int amount) {
//        return feedService.rotateForward(amount);
//    }
//
//    @GetMapping("/shake")
//    public ResponseEntity<String> shake(@RequestParam int amount, @RequestParam int stepsForward, @RequestParam int stepsBackward) {
//        return feedService.shakeTest(amount, stepsForward, stepsBackward);
//    }
}
