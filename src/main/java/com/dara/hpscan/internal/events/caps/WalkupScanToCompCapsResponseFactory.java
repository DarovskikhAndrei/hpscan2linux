package com.dara.hpscan.internal.events.caps;

import org.apache.http.HttpResponse;

import com.dara.hpscan.internal.events.IEventResultFactory;
import com.dara.hpscan.internal.events.IResponseAction;

/**
 * Данные запроса готовности результата сканирования
 */
public final class WalkupScanToCompCapsResponseFactory implements IEventResultFactory<WalkupScanToCompCapsResponse>
{
    @Override
    public WalkupScanToCompCapsResponse createData(HttpResponse response)
    {
        return new WalkupScanToCompCapsResponse();
    }

    @Override
    public IResponseAction createExecutor()
    {
        return new WalkupScanToCompCapsResponseAction();
    }
}
