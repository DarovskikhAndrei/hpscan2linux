package HPScan2Linux.HPScan2Linux.events;

import HPScan2Linux.HPScan2Linux.RequestHelper;

public interface IEvent
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
    RequestHelper getRequestHelper();
}
