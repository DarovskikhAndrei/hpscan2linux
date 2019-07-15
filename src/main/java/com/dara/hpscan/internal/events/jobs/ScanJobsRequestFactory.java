package com.dara.hpscan.internal.events.jobs;

import com.dara.hpscan.IRequestBodyProvider;
import com.dara.hpscan.internal.events.IEventRequest;
import com.dara.hpscan.internal.events.IEventRequestFactory;

/**
 * Запрос по умолчанию. Не имеет специальных обработок на сервере.
 */
public class ScanJobsRequestFactory implements IEventRequestFactory
{
    public static final ScanJobsRequestFactory INSTANCE = new ScanJobsRequestFactory();

    @Override
    public boolean acept(String url)
    {
        return url.equals("/Scan/Jobs");
    }

    @Override
    public IEventRequest create(String url, String type, IRequestBodyProvider IRequestBodyProvider)
    {
        return new ScanJobsRequest(url);
    }
}
