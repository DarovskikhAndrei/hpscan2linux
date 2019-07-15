package HPScan2Linux.HPScan2Linux.events;

import org.apache.http.HttpResponse;

/**
 * Данные запроса готовности результата сканирования
 */
public final class ScanJobsResponseFactory implements IEventResultFactory<ScanJobsResponse>
{
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
