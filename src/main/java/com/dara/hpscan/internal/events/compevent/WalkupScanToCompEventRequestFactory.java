package com.dara.hpscan.internal.events.compevent;

import com.dara.hpscan.IRequestBodyProvider;
import com.dara.hpscan.internal.events.IEventRequest;
import com.dara.hpscan.internal.events.IEventRequestFactory;

/**
 * Событие начала сканирования
 */
public final class WalkupScanToCompEventRequestFactory implements IEventRequestFactory
{
    public static final WalkupScanToCompEventRequestFactory INSTANCE = new WalkupScanToCompEventRequestFactory();

    private WalkupScanToCompEventRequestFactory()
    {

    }

    @Override
    public boolean aсcept(String url)
    {
        return url.startsWith("/WalkupScanToComp/WalkupScanToCompEvent");
    }

    @Override
    public IEventRequest create(String url, String type, String method,
                                IRequestBodyProvider IRequestBodyProvider)
    {
        return new WalkupScanToCompEventRequest(url);
    }
}
