package com.dara.hpscan.internal.events.compevent;

import org.apache.http.HttpResponse;

import com.dara.hpscan.internal.events.IEventResultFactory;
import com.dara.hpscan.internal.events.IResponseAction;

/**
 * Данные запроса готовности результата сканирования
 */
public final class WalkupScanToCompEventResponseFactory implements IEventResultFactory<WalkupScanToCompEventResponse>
{
    @Override
    public WalkupScanToCompEventResponse createData(HttpResponse response)
    {
        return WalkupScanToCompEventResponse.create(response);
    }

    @Override
    public IResponseAction createExecutor()
    {
        return new WalkupScanToCompEventResponseAction();
    }
}
