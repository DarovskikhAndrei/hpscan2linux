package com.dara.hpscan.internal.events.compdest;

import java.util.Collections;
import java.util.List;

import com.dara.hpscan.internal.events.IEventRequest;
import com.dara.hpscan.internal.events.IResponseAction;

public final class WalkupScanToCompDestinationResponseAction implements IResponseAction<Object>
{
    @Override
    public List<IEventRequest> execute(Object response)
    {
        return Collections.emptyList();
    }
}
