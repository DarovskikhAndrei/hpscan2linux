package com.dara.hpscan.internal.events.compdest;

import com.dara.hpscan.IRequestBodyProvider;
import com.dara.hpscan.internal.events.IEventRequest;
import com.dara.hpscan.internal.events.IEventRequestFactory;

/**
 * Запрос по умолчанию. Не имеет специальных обработок на сервере.
 *
 * Подходит для создания запроса с любым URL
 */
public final class WalkupScanToCompDestinationRequestFactory implements IEventRequestFactory
{
    public static final WalkupScanToCompDestinationRequestFactory INSTANCE = new WalkupScanToCompDestinationRequestFactory();

    @Override
    public boolean acept(String url)
    {
        return url.startsWith("/WalkupScanToComp/WalkupScanToCompDestinations");
    }

    @Override
    public IEventRequest create(String url, String type, IRequestBodyProvider IRequestBodyProvider)
    {
        return new WalkupScanToCompDestinationRequest(url, IRequestBodyProvider);
    }
}
