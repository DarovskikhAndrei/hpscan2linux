package HPScan2Linux.HPScan2Linux.events;

import org.apache.http.HttpResponse;

/**
 * Данные запроса готовности результата сканирования
 */
public final class WalkupScanToCompCapsResponseFactory implements IEventResultFactory<WalkupScanToCompCapsResponse>
{
    @Override
    public WalkupScanToCompCapsResponse createData(HttpResponse response)
    {
        return new WalkupScanToCompCapsResponse();
    }

    @Override
    public IResponseAction createExecutor()
    {
        return new WalkupScanToCompCapsResponseAction();
    }
}
