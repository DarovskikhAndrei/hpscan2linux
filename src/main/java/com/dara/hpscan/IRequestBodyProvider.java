package com.dara.hpscan;

/**
 * Интерфейс поставщика тела запроса
 */
public interface IRequestBodyProvider
{
    /**
     * Возвращает тело запроса в виде строки
     */
    String getBody();

    /**
     * Возвращает тип контента
     */
    String contentType();
}
