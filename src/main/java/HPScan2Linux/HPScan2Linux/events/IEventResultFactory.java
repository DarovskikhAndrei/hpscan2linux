package HPScan2Linux.HPScan2Linux.events;

import org.apache.http.HttpResponse;

/**
 * Абстрактная фабрика для получения и обработки результатов события
 */
public interface IEventResultFactory<TData>
{
    /**
     * Создает объект данных из http ответа
     * @param response отв6ет
     * @return созданный объект данных
     */
    TData createData(HttpResponse response);

    /**
     * Создает обработчик результатов событий
     */
    IResponseAction createExecutor();
}
