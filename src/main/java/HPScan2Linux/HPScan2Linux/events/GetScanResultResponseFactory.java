package HPScan2Linux.HPScan2Linux.events;

import org.apache.http.HttpResponse;

/**
 * Данные запроса готовности результата сканирования
 */
public final class GetScanResultResponseFactory implements IEventResultFactory<GetScanResultResponse>
{
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
