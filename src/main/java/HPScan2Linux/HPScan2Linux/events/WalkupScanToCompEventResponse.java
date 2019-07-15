package HPScan2Linux.HPScan2Linux.events;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.w3c.dom.Document;

public final class WalkupScanToCompEventResponse
{
    private final String eventType;

    public static WalkupScanToCompEventResponse create(HttpResponse response)
    {
        try(InputStream inStream = ResponseExecutor.getBodyStream(response))
        {
            Document doc = ResponseExecutor.getXMLDocument(inStream);

            final String eventType = ResponseExecutor.getXMLParam(doc, "WalkupScanToCompEventType",
                    "http://www.hp.com/schemas/imaging/con/ledm/walkupscan/2010/09/28");

            return new WalkupScanToCompEventResponse(eventType);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private WalkupScanToCompEventResponse(String eventType)
    {
        this.eventType = eventType;
    }

    public String getEventType()
    {
        return eventType;
    }

    public boolean scanRequested()
    {
        return eventType.equalsIgnoreCase("ScanRequested");
    }
}
