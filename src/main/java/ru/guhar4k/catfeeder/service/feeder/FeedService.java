package ru.guhar4k.catfeeder.service.feeder;

import org.springframework.http.ResponseEntity;
import ru.guhar4k.catfeeder.model.dto.FeedResultDTO;
import ru.guhar4k.catfeeder.model.enumeration.Direction;

/**
 * Сервис выдачи корма
 */
public interface FeedService {

    /**
     * Выдача необходимого количества корма
     * @param amount необходимое количество в граммах
     * @return информация о выполненной операции
     */
    FeedResultDTO feed(int amount);

//    ResponseEntity<String> rotateForward(int count);
//
//    ResponseEntity<String> shakeTest(int shakes, int stepsForward, int stepsBackward);
}
