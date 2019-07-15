package HPScan2Linux.HPScan2Linux.events;

import java.util.Collections;
import java.util.List;

public final class WalkupScanToCompCapsResponseAction implements IResponseAction<Object>
{
    @Override
    public List<IEvent> execute(Object response)
    {
        return Collections.emptyList();
    }
}
