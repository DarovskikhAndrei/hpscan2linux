package com.dara.hpscan.internal.events.jobs;

import org.apache.http.HttpResponse;

import com.dara.hpscan.internal.events.IEventResultFactory;
import com.dara.hpscan.internal.events.IResponseAction;

/**
 * Данные запроса готовности результата сканирования
 */
public final class ScanJobsResponseFactory implements IEventResultFactory<ScanJobsResponse>
{
    public static final ScanJobsResponseFactory INSTANCE = new ScanJobsResponseFactory();

    private ScanJobsResponseFactory()
    {

    }

    @Override
    public ScanJobsResponse createData(HttpResponse response)
    {
        return ScanJobsResponse.create(response);
    }

    @Override
    public IResponseAction createExecutor()
    {
        return new ScanJobsResponseAction();
    }
}
