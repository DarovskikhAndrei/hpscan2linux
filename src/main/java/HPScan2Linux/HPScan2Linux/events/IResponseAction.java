package HPScan2Linux.HPScan2Linux.events;

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
    List<IEvent> execute(TData data);
}
