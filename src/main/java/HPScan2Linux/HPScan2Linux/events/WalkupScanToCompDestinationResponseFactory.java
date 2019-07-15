package HPScan2Linux.HPScan2Linux.events;

import org.apache.http.HttpResponse;

/**
 * Данные запроса готовности результата сканирования
 */
public final class WalkupScanToCompDestinationResponseFactory implements IEventResultFactory<WalkupScanToCompDestinationResponse>
{
    @Override
    public WalkupScanToCompDestinationResponse createData(HttpResponse response)
    {
        return WalkupScanToCompDestinationResponse.create(response);
    }

    @Override
    public IResponseAction createExecutor()
    {
        return new WalkupScanToCompCapsResponseAction();
    }
}
