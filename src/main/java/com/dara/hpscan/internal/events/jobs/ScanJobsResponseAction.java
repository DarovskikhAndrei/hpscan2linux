package com.dara.hpscan.internal.events.jobs;

import java.util.Arrays;
import java.util.List;

import com.dara.hpscan.internal.events.EventFactory;
import com.dara.hpscan.internal.events.IEventRequest;
import com.dara.hpscan.internal.events.IResponseAction;

public final class ScanJobsResponseAction implements IResponseAction<ScanJobsResponse>
{
    @Override
    public List<IEventRequest> execute(ScanJobsResponse response)
    {
        return Arrays.asList(EventFactory.createEvent(response.getUrl()));
    }
}
