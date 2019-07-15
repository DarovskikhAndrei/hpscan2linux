package com.dara.hpscan.internal.events.compevent;

import org.apache.http.HttpResponse;

import com.dara.hpscan.internal.events.IEventResultFactory;
import com.dara.hpscan.internal.events.IResponseAction;

/**
 * Данные запроса готовности результата сканирования
 */
public final class WalkupScanToCompEventResponseFactory implements IEventResultFactory<WalkupScanToCompEventResponse>
{
    public static final WalkupScanToCompEventResponseFactory INSTANCE = new WalkupScanToCompEventResponseFactory();

    private WalkupScanToCompEventResponseFactory()
    {

    }

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
