package com.dara.hpscan.internal.events.caps;

import com.dara.hpscan.IRequestBodyProvider;
import com.dara.hpscan.internal.events.IEventRequest;
import com.dara.hpscan.internal.events.IEventResultFactory;
import com.dara.hpscan.internal.events.def.DefaultEventResponseFactory;

/**
 * Запрос по умолчанию. Не имеет специальных обработок на сервере.
 */
public class WalkupScanToCompCapsRequest implements IEventRequest
{
    private final String resourceType;
    private final String resourceURI;
    private final String method;

    public WalkupScanToCompCapsRequest(String url, String type, String method)
    {
        this.resourceURI = url;
        this.resourceType = type;
        this.method = method;
    }

    public WalkupScanToCompCapsRequest(String url, String type)
    {
        this.resourceURI = url;
        this.resourceType = type;
        this.method = "GET";
    }

    @Override
    public String getResourceURL()
    {
        return resourceURI;
    }

    @Override
    public String getHttpMethod()
    {
        return method;
    }

    @Override
    public IEventResultFactory getEventResultFactory()
    {
        return DefaultEventResponseFactory.INSTANCE;
    }

    @Override
    public IRequestBodyProvider getIRequestBodyProvider()
    {
        return null;
    }
}
