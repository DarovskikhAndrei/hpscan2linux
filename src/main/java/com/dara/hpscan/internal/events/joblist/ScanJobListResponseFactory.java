package com.dara.hpscan.internal.events.joblist;

import org.apache.http.HttpResponse;

import com.dara.hpscan.internal.events.IEventResultFactory;
import com.dara.hpscan.internal.events.IResponseAction;

/**
 * Данные запроса готовности результата сканирования
 */
public final class ScanJobListResponseFactory implements IEventResultFactory<ScanJobListResponse>
{
    public static final ScanJobListResponseFactory INSTANCE = new ScanJobListResponseFactory();

    private ScanJobListResponseFactory()
    {

    }

    @Override
    public ScanJobListResponse createData(HttpResponse response)
    {
        return ScanJobListResponse.create(response);
    }

    @Override
    public IResponseAction createExecutor()
    {
        return new ScanJobListResponseAction();
    }
}
