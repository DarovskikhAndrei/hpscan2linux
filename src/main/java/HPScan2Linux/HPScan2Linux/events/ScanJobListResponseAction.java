package HPScan2Linux.HPScan2Linux.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ScanJobListResponseAction implements IResponseAction<ScanJobListResponse>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(EventFactory.class);

    @Override
    public List<IEvent> execute(ScanJobListResponse responseData)
    {
        List<IEvent> newEvents = new ArrayList<>();

        if (responseData.readyToUpload())
        {
            System.err.println("Add download event with url: " + responseData.getJobBinaryURL());
            return Arrays.asList(new Event(responseData.getJobBinaryURL(), "", "GET", new GetScanResultResponseFactory(), null));
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
