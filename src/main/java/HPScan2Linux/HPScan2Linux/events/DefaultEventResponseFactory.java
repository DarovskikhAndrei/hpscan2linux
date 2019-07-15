package HPScan2Linux.HPScan2Linux.events;

import org.apache.http.HttpResponse;

public final class DefaultEventResponseFactory implements IEventResultFactory<DefaultEventResponse>
{
    @Override
    public DefaultEventResponse createData(HttpResponse response)
    {
        return DefaultEventResponse.create(response);
    }

    @Override
    public IResponseAction createExecutor()
    {
        return new DefaultEventResponseAction();
    }
}