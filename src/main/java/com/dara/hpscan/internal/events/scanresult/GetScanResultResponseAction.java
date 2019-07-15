package com.dara.hpscan.internal.events.scanresult;

import java.util.Collections;
import java.util.List;

import com.dara.hpscan.internal.events.IEventRequest;
import com.dara.hpscan.internal.events.IResponseAction;

public final class GetScanResultResponseAction implements IResponseAction<GetScanResultResponse>
{
    @Override
    public List<IEventRequest> execute(GetScanResultResponse getScanResultResponse)
    {
        return Collections.emptyList();
    }
}
