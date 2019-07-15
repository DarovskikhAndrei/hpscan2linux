package com.dara.hpscan.internal.events.compevent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.dara.hpscan.internal.events.EventFactory;
import com.dara.hpscan.internal.events.IEventRequest;
import com.dara.hpscan.internal.events.IResponseAction;

public final class WalkupScanToCompEventResponseAction implements IResponseAction<WalkupScanToCompEventResponse>
{
    @Override
    public List<IEventRequest> execute(WalkupScanToCompEventResponse response)
    {
        if (response.scanRequested())
        {
            return Arrays.asList(EventFactory.createEvent("/Scan/Status"));
        }

        return Collections.emptyList();
    }
}
