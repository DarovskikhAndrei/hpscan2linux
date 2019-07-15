package HPScan2Linux.HPScan2Linux.events;

import java.util.List;

public final class DefaultEventResponseAction implements IResponseAction<DefaultEventResponse>
{
    @Override
    public List<IEvent> execute(DefaultEventResponse defaultEventResponse)
    {
        return defaultEventResponse.getSubEvents();
    }
}