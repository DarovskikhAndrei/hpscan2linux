package com.dara.hpscan.internal.events.scanresult;

import org.apache.http.HttpResponse;

import com.dara.hpscan.internal.events.IEventResultFactory;
import com.dara.hpscan.internal.events.IResponseAction;

/**
 * Данные запроса готовности результата сканирования
 */
public final class GetScanResultResponseFactory implements IEventResultFactory<GetScanResultResponse>
{
    public static final GetScanResultResponseFactory INSTANCE = new GetScanResultResponseFactory();

    private GetScanResultResponseFactory()
    {

    }

    @Override
    public GetScanResultResponse createData(HttpResponse response)
    {
        return GetScanResultResponse.create(response);
    }

    @Override
    public IResponseAction createExecutor()
    {
        return new GetScanResultResponseAction();
    }
}
