package com.dara.hpscan.internal.events.scanstatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dara.hpscan.StateService;
import com.dara.hpscan.internal.events.EventFactory;
import com.dara.hpscan.internal.events.IEventRequest;
import com.dara.hpscan.internal.events.IResponseAction;

public final class ScanStatusResponseAction implements IResponseAction<ScanStatusResponse>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ScanStatusResponseAction.class);
    
    @Override
    public List<IEventRequest> execute(ScanStatusResponse response)
    {
        // TODO. Избавиться от глобального состояния
        if (response.idle()
                && StateService.getInstance().getProfile() != null)
        {
            return Arrays.asList(EventFactory.createEvent("/Scan/Jobs", "", new ScanJobsRequest()));
        }

        return Collections.emptyList();
    }
}