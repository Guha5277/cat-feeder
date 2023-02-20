package ru.guhar4k.catfeeder.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequiredArgsConstructor
@RequestMapping("/feeder")
@Api(tags = "Управление фидером")
public class FeederApiController {
    private final FeedService feedService;

    @GetMapping("/feed")
    @ApiOperation("Вызов задач фидера - выдача корма")
    public ResponseEntity<FeedResultDTO> feed(@Schema(name = "amount", description = "Количество выдаваемого корма в граммах") @RequestParam int amount) {
        FeedResultDTO result = feedService.feed(amount);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
