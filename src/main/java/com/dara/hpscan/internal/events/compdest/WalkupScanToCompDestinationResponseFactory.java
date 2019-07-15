package com.dara.hpscan.internal.events.compdest;

import org.apache.http.HttpResponse;

import com.dara.hpscan.internal.events.IEventResultFactory;
import com.dara.hpscan.internal.events.IResponseAction;

/**
 * Данные запроса готовности результата сканирования
 */
public final class WalkupScanToCompDestinationResponseFactory implements IEventResultFactory<WalkupScanToCompDestinationResponse>
{
    public static final WalkupScanToCompDestinationResponseFactory INSTANCE = new WalkupScanToCompDestinationResponseFactory();

    private WalkupScanToCompDestinationResponseFactory()
    {

    }

    @Override
    public WalkupScanToCompDestinationResponse createData(HttpResponse response)
    {
        return WalkupScanToCompDestinationResponse.create(response);
    }

    @Override
    public IResponseAction createExecutor()
    {
        return new WalkupScanToCompDestinationResponseAction();
    }
}
