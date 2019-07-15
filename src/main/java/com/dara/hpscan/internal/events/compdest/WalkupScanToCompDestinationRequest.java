package com.dara.hpscan.internal.events.compdest;

import com.dara.hpscan.IRequestBodyProvider;
import com.dara.hpscan.internal.events.IEventRequest;
import com.dara.hpscan.internal.events.IEventResultFactory;

/**
 * Запрос по умолчанию. Не имеет специальных обработок на сервере.
 */
public class WalkupScanToCompDestinationRequest implements IEventRequest
{
    private final IRequestBodyProvider IRequestBodyProvider;
    private final String resourceURL;
    private final String method;

    public WalkupScanToCompDestinationRequest(String resourceURL, IRequestBodyProvider IRequestBodyProvider)
    {
        this.resourceURL = resourceURL;
        this.IRequestBodyProvider = IRequestBodyProvider;
        this.method = IRequestBodyProvider == null ? "GET" : "POST";
    }

    @Override
    public String getResourceURL()
    {
        return resourceURL;
    }

    @Override
    public String getHttpMethod()
    {
        return method;
    }

    @Override
    public IEventResultFactory getEventResultFactory()
    {
        return WalkupScanToCompDestinationResponseFactory.INSTANCE;
    }

    @Override
    public IRequestBodyProvider getIRequestBodyProvider()
    {
        return IRequestBodyProvider;
    }
}
