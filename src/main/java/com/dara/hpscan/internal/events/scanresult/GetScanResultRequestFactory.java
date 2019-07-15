package com.dara.hpscan.internal.events.scanresult;

import com.dara.hpscan.IRequestBodyProvider;
import com.dara.hpscan.internal.events.IEventRequest;
import com.dara.hpscan.internal.events.IEventRequestFactory;

/**
 * Запрос по умолчанию. Не имеет специальных обработок на сервере.
 */
public class GetScanResultRequestFactory implements IEventRequestFactory
{
    public static final GetScanResultRequestFactory INSTANCE = new GetScanResultRequestFactory();

    @Override
    public boolean acept(String url)
    {
        // TODO. номер работы тут может меняться?
        return url.startsWith("/Scan/Jobs/1/Pages");
    }

    @Override
    public IEventRequest create(String url, String type, IRequestBodyProvider IRequestBodyProvider)
    {
        return new GetScanResultRequest(url);
    }
}
