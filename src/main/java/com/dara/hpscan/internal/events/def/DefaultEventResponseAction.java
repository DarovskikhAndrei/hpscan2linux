package com.dara.hpscan.internal.events.def;

import java.util.Collections;
import java.util.List;

import com.dara.hpscan.internal.events.IEventRequest;
import com.dara.hpscan.internal.events.IResponseAction;

public final class DefaultEventResponseAction implements IResponseAction<DefaultEventResponse>
{
    @Override
    public List<IEventRequest> execute(DefaultEventResponse defaultEventResponse)
    {
        if (defaultEventResponse == null)
            return Collections.emptyList();

        return defaultEventResponse.getSubEvents();
    }
}