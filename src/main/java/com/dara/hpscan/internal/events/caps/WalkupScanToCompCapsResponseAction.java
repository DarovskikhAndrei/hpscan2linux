package com.dara.hpscan.internal.events.caps;

import java.util.Collections;
import java.util.List;

import com.dara.hpscan.internal.events.IEventRequest;
import com.dara.hpscan.internal.events.IResponseAction;

public final class WalkupScanToCompCapsResponseAction implements IResponseAction<Object>
{
    @Override
    public List<IEventRequest> execute(Object response)
    {
        return Collections.emptyList();
    }
}
