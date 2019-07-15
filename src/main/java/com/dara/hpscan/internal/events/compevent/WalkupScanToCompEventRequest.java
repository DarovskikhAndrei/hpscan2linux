package com.dara.hpscan.internal.events.compevent;

import com.dara.hpscan.IRequestBodyProvider;
import com.dara.hpscan.internal.events.IEventRequest;
import com.dara.hpscan.internal.events.IEventResultFactory;
import com.dara.hpscan.internal.events.joblist.ScanJobListResponseFactory;

/**
 * Данные запроса готовности результата сканирования
 */
public final class WalkupScanToCompEventRequest implements IEventRequest
{
    private final String resourceURL;

    public WalkupScanToCompEventRequest(String resourceURL)
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
        return WalkupScanToCompEventResponseFactory.INSTANCE;
    }

    @Override
    public IRequestBodyProvider getIRequestBodyProvider()
    {
        return null;
    }
}
