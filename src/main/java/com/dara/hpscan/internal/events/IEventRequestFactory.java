package com.dara.hpscan.internal.events;

import com.dara.hpscan.IRequestBodyProvider;

/**
 * Общий интерфейс фабрики запроса по умолчанию
 */
public interface IEventRequestFactory
{
    /**
     * Возвращает признак применимости заданного url к объектам этой фабрики
     */
    boolean acept(String url);

    /**
     * Создать объект по url
     *
     * @param url адрес ресурса
     * @param type тип ресурса
     */
    IEventRequest create(String url, String type, IRequestBodyProvider IRequestBodyProvider);
}
