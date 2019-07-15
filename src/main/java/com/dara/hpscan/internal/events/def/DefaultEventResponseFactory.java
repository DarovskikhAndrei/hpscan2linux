package com.dara.hpscan.internal.events.def;

import org.apache.http.HttpResponse;

import com.dara.hpscan.internal.events.IEventResultFactory;
import com.dara.hpscan.internal.events.IResponseAction;

public final class DefaultEventResponseFactory implements IEventResultFactory<DefaultEventResponse>
{
    public static final DefaultEventResponseFactory INSTANCE = new DefaultEventResponseFactory();

    private DefaultEventResponseFactory()
    {

    }

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