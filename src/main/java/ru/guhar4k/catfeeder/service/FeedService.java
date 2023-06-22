package ru.guhar4k.catfeeder.service;

import ru.guhar4k.catfeeder.model.dto.FeedResultDTO;

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
}
