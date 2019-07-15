package com.dara.hpscan.internal.events.def;

import com.dara.hpscan.IRequestBodyProvider;
import com.dara.hpscan.internal.events.IEventRequest;
import com.dara.hpscan.internal.events.IEventRequestFactory;

/**
 * Запрос по умолчанию. Не имеет специальных обработок на сервере.
 *
 * Подходит для создания запроса с любым URL
 */
public final class DefaultEventRequestFactory implements IEventRequestFactory
{
    public static final DefaultEventRequestFactory INSTANCE = new DefaultEventRequestFactory();

    @Override
    public boolean aсcept(String url)
    {
        return true;
    }

    @Override
    public IEventRequest create(String url, String type, String method,
                                IRequestBodyProvider IRequestBodyProvider)
    {
        return new DefaultEventRequest(url, type);
    }
}
