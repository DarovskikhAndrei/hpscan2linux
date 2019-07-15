package com.dara.hpscan.internal.events;

import java.util.List;

/**
 * Обработка ответа сканера
 */
public interface IResponseAction<TData>
{
    /**
     * Выполняет обработку результата
     *
     * Возвращает список событий, которые необходимо добавить в очередь для обработки
     */
    List<IEventRequest> execute(TData data);
}
