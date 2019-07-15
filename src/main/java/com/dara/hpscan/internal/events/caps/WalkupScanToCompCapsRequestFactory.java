package com.dara.hpscan.internal.events.caps;

import com.dara.hpscan.IRequestBodyProvider;
import com.dara.hpscan.internal.events.IEventRequest;
import com.dara.hpscan.internal.events.IEventRequestFactory;

/**
 * Запрос по умолчанию. Не имеет специальных обработок на сервере.
 *
 * Подходит для создания запроса с любым URL
 */
public final class WalkupScanToCompCapsRequestFactory implements IEventRequestFactory
{
    public static final WalkupScanToCompCapsRequestFactory INSTANCE = new WalkupScanToCompCapsRequestFactory();

    @Override
    public boolean aсcept(String url)
    {
        return url.startsWith("/WalkupScanToComp/WalkupScanToCompCaps");
    }

    @Override
    public IEventRequest create(String url, String type, String method,
                                IRequestBodyProvider IRequestBodyProvider)
    {
        return new WalkupScanToCompCapsRequest(url, type);
    }
}
