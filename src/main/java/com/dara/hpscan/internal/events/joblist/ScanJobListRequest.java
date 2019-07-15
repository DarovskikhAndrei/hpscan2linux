package com.dara.hpscan.internal.events.joblist;

import com.dara.hpscan.IRequestBodyProvider;
import com.dara.hpscan.internal.events.IEventRequest;
import com.dara.hpscan.internal.events.IEventResultFactory;

/**
 * Данные запроса готовности результата сканирования
 */
public final class ScanJobListRequest implements IEventRequest
{
    private final String resourceURL;

    public ScanJobListRequest(String resourceURL)
    {
        this.resourceURL = resourceURL;
    }

    @Override
    public String getHttpMethod()
    {
        return "GET";
    }

    @Override
    public String getResourceURL()
    {
        return resourceURL;
    }

    @Override
    public IEventResultFactory getEventResultFactory()
    {
        return ScanJobListResponseFactory.INSTANCE;
    }

    @Override
    public IRequestBodyProvider getIRequestBodyProvider()
    {
        return null;
    }
}
