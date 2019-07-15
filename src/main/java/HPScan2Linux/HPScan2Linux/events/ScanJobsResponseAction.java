package HPScan2Linux.HPScan2Linux.events;

import java.util.Arrays;
import java.util.List;

public final class ScanJobsResponseAction implements IResponseAction<ScanJobsResponse>
{
    @Override
    public List<IEvent> execute(ScanJobsResponse response)
    {
        return Arrays.asList(EventFactory.createEvent(response.getUrl()));
    }
}
