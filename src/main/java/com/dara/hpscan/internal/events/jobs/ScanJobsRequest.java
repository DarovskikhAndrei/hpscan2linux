package com.dara.hpscan.internal.events.jobs;

import com.dara.hpscan.IRequestBodyProvider;
import com.dara.hpscan.internal.events.IEventRequest;
import com.dara.hpscan.internal.events.IEventResultFactory;
import com.dara.hpscan.internal.events.scanresult.GetScanResultResponseFactory;

/**
 * Запрос по умолчанию. Не имеет специальных обработок на сервере.
 */
public class ScanJobsRequest implements IEventRequest
{
    private final String resourceURI;
    private final String method;
    private final IRequestBodyProvider requestBodyProvider;

    public ScanJobsRequest(String url, String method, IRequestBodyProvider requestBodyProvider)
    {
        this.resourceURI = url;
        this.method = method;
        this.requestBodyProvider = requestBodyProvider;
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
        return ScanJobsResponseFactory.INSTANCE;
    }

    @Override
    public IRequestBodyProvider getIRequestBodyProvider()
    {
        return requestBodyProvider;
    }
}
