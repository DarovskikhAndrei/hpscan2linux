package com.dara.hpscan.internal.events.scanstatus;

import org.apache.http.HttpResponse;

import com.dara.hpscan.internal.events.IEventResultFactory;
import com.dara.hpscan.internal.events.IResponseAction;

/**
 * Данные запроса готовности результата сканирования
 */
public final class ScanStatusResponseFactory implements IEventResultFactory<ScanStatusResponse>
{
    public static final ScanStatusResponseFactory INSTANCE = new ScanStatusResponseFactory();

    private ScanStatusResponseFactory()
    {

    }

    @Override
    public ScanStatusResponse createData(HttpResponse response)
    {
        return ScanStatusResponse.create(response);
    }

    @Override
    public IResponseAction createExecutor()
    {
        return new ScanStatusResponseAction();
    }
}
