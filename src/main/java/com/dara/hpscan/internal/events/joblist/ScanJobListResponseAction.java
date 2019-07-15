package com.dara.hpscan.internal.events.joblist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dara.hpscan.internal.events.EventFactory;
import com.dara.hpscan.internal.events.IEventRequest;
import com.dara.hpscan.internal.events.IResponseAction;
import com.dara.hpscan.internal.events.scanresult.GetScanResultRequest;

public final class ScanJobListResponseAction implements IResponseAction<ScanJobListResponse>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(EventFactory.class);

    @Override
    public List<IEventRequest> execute(ScanJobListResponse responseData)
    {
        if (responseData.readyToUpload())
        {
            return Arrays.asList(new GetScanResultRequest(responseData.getJobBinaryURL()));
        }
        else if (responseData.canceled())
        {
            return Arrays.asList(EventFactory.createEvent("/EventMgmt/EventTable"));
        }
        else
        {
            try
            {
                Thread.sleep(500);
            }
            catch (InterruptedException e)
            {
            }
            // если задание не выполнено.. повторим запрос.
            return Arrays.asList(EventFactory.createEvent(responseData.getJobURL()));
        }
    }
}
