package HPScan2Linux.HPScan2Linux.events;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class WalkupScanToCompEventResponseAction implements IResponseAction<WalkupScanToCompEventResponse>
{
    @Override
    public List<IEvent> execute(WalkupScanToCompEventResponse response)
    {
        if (response.scanRequested())
        {
            return Arrays.asList(EventFactory.createEvent("/Scan/Status"));
        }

        return Collections.emptyList();
    }
}
