package HPScan2Linux.HPScan2Linux.events;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import HPScan2Linux.HPScan2Linux.ScanJobsRequest;
import HPScan2Linux.HPScan2Linux.StateService;

public final class ScanStatusResponseAction implements IResponseAction<ScanStatusResponse>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ScanStatusResponseAction.class);
    
    @Override
    public List<IEvent> execute(ScanStatusResponse response)
    {
        // TODO. Избавиться от глобального состояния
        if (response.idle()
                && StateService.getInstance().getProfile() != null)
        {
            return Arrays.asList(EventFactory.createEvent("/Scan/Jobs", new ScanJobsRequest()));
        }

        return Collections.emptyList();
    }
}