package HPScan2Linux.HPScan2Linux.events;

import org.apache.http.HttpResponse;

/**
 * Данные запроса готовности результата сканирования
 */
public final class ScanStatusResponseFactory implements IEventResultFactory<ScanStatusResponse>
{
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
