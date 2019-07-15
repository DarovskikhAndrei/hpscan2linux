package com.dara.hpscan.internal.events.joblist;

import com.dara.hpscan.IRequestBodyProvider;
import com.dara.hpscan.internal.events.IEventRequest;
import com.dara.hpscan.internal.events.IEventRequestFactory;

/**
 * Данные запроса готовности результата сканирования
 */
public final class ScanJobListRequestFactory implements IEventRequestFactory
{
    public static final ScanJobListRequestFactory INSTANCE = new ScanJobListRequestFactory();

    private ScanJobListRequestFactory()
    {

    }

    @Override
    public boolean aсcept(String url)
    {
        return url.startsWith("/Jobs/JobList/");
    }

    @Override
    public IEventRequest create(String url, String type, String method,
                                IRequestBodyProvider IRequestBodyProvider)
    {
        return new ScanJobListRequest(url);
    }
}
