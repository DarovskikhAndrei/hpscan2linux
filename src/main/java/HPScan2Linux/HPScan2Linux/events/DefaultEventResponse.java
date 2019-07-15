package HPScan2Linux.HPScan2Linux.events;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public final class DefaultEventResponse
{
    private final String eventName;
    private final List<IEvent> events;

    public DefaultEventResponse(String eventName, List<IEvent> events)
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
        List<IEvent> events = new ArrayList<>();
        try(InputStream inStream = ResponseExecutor.getBodyStream(response))
        {
            String eventName = "defaultName";
            Document doc = ResponseExecutor.getXMLDocument(inStream);

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
                    events.add(Event.from(elem));
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

    public List<IEvent> getSubEvents()
    {
        return events;
    }
}