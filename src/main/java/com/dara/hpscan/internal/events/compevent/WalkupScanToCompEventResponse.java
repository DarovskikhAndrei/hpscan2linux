package com.dara.hpscan.internal.events.compevent;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.w3c.dom.Document;

import com.dara.hpscan.internal.ResponseExecutorHelper;

public final class WalkupScanToCompEventResponse
{
    private final String eventType;

    public static WalkupScanToCompEventResponse create(HttpResponse response)
    {
        try(InputStream inStream = ResponseExecutorHelper.getBodyStream(response))
        {
            Document doc = ResponseExecutorHelper.getXMLDocument(inStream);

            final String eventType = ResponseExecutorHelper.getXMLParam(doc, "WalkupScanToCompEventType",
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
