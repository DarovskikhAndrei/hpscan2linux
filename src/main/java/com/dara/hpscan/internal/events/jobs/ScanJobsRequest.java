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

    public ScanJobsRequest(String url)
    {
        this.resourceURI = url;
    }

    @Override
    public String getResourceURL()
    {
        return resourceURI;
    }

    @Override
    public String getHttpMethod()
    {
        return "GET";
    }

    @Override
    public IEventResultFactory getEventResultFactory()
    {
        return GetScanResultResponseFactory.INSTANCE;
    }

    @Override
    public IRequestBodyProvider getIRequestBodyProvider()
    {
        return null;
    }
}
