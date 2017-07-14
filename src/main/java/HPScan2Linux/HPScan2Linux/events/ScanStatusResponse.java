package HPScan2Linux.HPScan2Linux.events;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import HPScan2Linux.HPScan2Linux.ScanJobsRequest;
import HPScan2Linux.HPScan2Linux.StateService;

public final class ScanStatusResponse extends ResponseExecutor
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ScanStatusResponse.class);
    
    @Override
    public void execute()
    {
        if (m_scanStatus.equalsIgnoreCase("Idle") && StateService.getInstance().getProfile() != null)
        {
            m_events.add(EventFactory.createEvent("/Scan/Jobs", new ScanJobsRequest()));
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

            m_scanStatus = getXMLParam(doc, "ScannerState",
                    "http://www.hp.com/schemas/imaging/con/cnx/scan/2008/08/19");
            LOGGER.info("Scaner status: {}", m_scanStatus);
        }
        catch (ParserConfigurationException e)
        {
            LOGGER.error(e.getMessage());
        }
        catch (SAXException e)
        {
            LOGGER.error(e.getMessage());
        }
        catch (IOException e)
        {
            LOGGER.error(e.getMessage());
        }
    }

    private String m_scanStatus;
}