package com.dara.hpscan.internal.events;

import com.dara.hpscan.IRequestBodyProvider;

/**
 * Запрос к сканеру
 */
public interface IEventRequest
{
    /**
     * Возвращает HTTP метод события
     */
    String getHttpMethod();

    /**
     * Возвращает URL ресурса события
     */
    String getResourceURL();

    /**
     * Возвращает абстрактную фабрику для создания объектов и обработчиков результатов
     */
    IEventResultFactory getEventResultFactory();

    // TODO. Временное. Надо переделать
    /**
     * Помощник для формирования тела запроса
     */
    IRequestBodyProvider getIRequestBodyProvider();
}
