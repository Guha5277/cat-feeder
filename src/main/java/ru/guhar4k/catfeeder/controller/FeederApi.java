package ru.guhar4k.catfeeder.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.guhar4k.catfeeder.model.dto.FeedResultDTO;

@RequestMapping("/feeder")
@Schema(name = "Управление фидером", description = "Вызов задач фидера - выдача корма")
public interface FeederApi {

    @GetMapping
    @Schema(name = "Управление фидером", description = "Вызов задач фидера - выдача корма")
    ResponseEntity<FeedResultDTO> feed(
            @Schema(name = "amount", description = "Количество выдаваемого корма в граммах")
            @RequestParam int amount);
}
