package com.dara.hpscan.internal.events.def;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import com.dara.hpscan.internal.events.EventFactory;
import org.apache.http.HttpResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.dara.hpscan.internal.events.IEventRequest;
import com.dara.hpscan.internal.ResponseExecutorHelper;

public final class DefaultEventResponse
{
    private final String eventName;
    private final List<IEventRequest> events;

    public DefaultEventResponse(String eventName, List<IEventRequest> events)
    {
        this.eventName = eventName;
        this.events = events;
    }

    public String getEventCategory()
    {
        return eventName;
    }

    public static DefaultEventResponse create(HttpResponse response)
    {
        List<IEventRequest> events = new ArrayList<>();
        try(InputStream inStream = ResponseExecutorHelper.getBodyStream(response))
        {
            String eventName = "defaultName";
            Document doc = ResponseExecutorHelper.getXMLDocument(inStream);

            NodeList elems = doc.getElementsByTagNameNS("http://www.hp.com/schemas/imaging/con/dictionaries/1.0/",
                    "UnqualifiedEventCategory");
            if (elems.getLength() > 0)
                eventName = elems.item(0).getTextContent();

            elems = doc.getElementsByTagNameNS("http://www.hp.com/schemas/imaging/con/ledm/events/2007/09/16",
                    "Payload");

            for (int index = 0; index < elems.getLength(); ++index)
            {
                Element elem = (Element) elems.item(index);
                if (elem != null)
                {
                    events.add(EventFactory.fromElement(elem));
                }
            }

            return new DefaultEventResponse(eventName, events);
        }
        catch (ParserConfigurationException e)
        {
        }
        catch (SAXException e)
        {
        }
        catch (IOException e)
        {
        }

        return null;
    }

    public List<IEventRequest> getSubEvents()
    {
        return events;
    }
}