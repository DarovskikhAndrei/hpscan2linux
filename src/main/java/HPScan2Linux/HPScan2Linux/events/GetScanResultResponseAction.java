package HPScan2Linux.HPScan2Linux.events;

import java.util.Collections;
import java.util.List;

public final class GetScanResultResponseAction implements IResponseAction<GetScanResultResponse>
{
    @Override
    public List<IEvent> execute(GetScanResultResponse getScanResultResponse)
    {
        return Collections.emptyList();
    }
}
