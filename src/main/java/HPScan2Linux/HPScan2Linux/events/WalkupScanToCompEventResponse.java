package HPScan2Linux.HPScan2Linux.events;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public final class WalkupScanToCompEventResponse extends ResponseExecutor
{
    @Override
    public void execute()
    {
        if (m_eventType.equalsIgnoreCase("ScanRequested"))
        {
            m_events.add(EventFactory.createEvent("/Scan/Status"));
        }
    }

    @Override
    public void init(HttpResponse response)
    {
        InputStream inStream = getBodyStream(response);
        if (inStream == null)
            return;

        try
        {
            Document doc = getXMLDocument(inStream);

            m_eventType = getXMLParam(doc, "WalkupScanToCompEventType",
                    "http://www.hp.com/schemas/imaging/con/ledm/walkupscan/2010/09/28");
        }
        catch (ParserConfigurationException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (SAXException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getEventType()
    {
        return m_eventType;
    }

    private String m_eventType;
}
