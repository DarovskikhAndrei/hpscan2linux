package com.dara.hpscan.internal.events.scanstatus;

import com.dara.hpscan.IRequestBodyProvider;
import com.dara.hpscan.internal.events.IEventRequest;
import com.dara.hpscan.internal.events.IEventRequestFactory;

/**
 * Данные запроса готовности результата сканирования
 */
public final class ScanStatusRequestFactory implements IEventRequestFactory
{
    public static final ScanStatusRequestFactory INSTANCE = new ScanStatusRequestFactory();

    private ScanStatusRequestFactory()
    {

    }

    @Override
    public boolean aсcept(String url)
    {
        return url.equals("/Scan/Status");
    }

    @Override
    public IEventRequest create(String url, String type, String method,
                                IRequestBodyProvider IRequestBodyProvider)
    {
        return new ScanStatusRequest(url);
    }
}
