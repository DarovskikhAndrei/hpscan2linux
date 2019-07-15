package HPScan2Linux.HPScan2Linux.events;

import org.apache.http.HttpResponse;

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
