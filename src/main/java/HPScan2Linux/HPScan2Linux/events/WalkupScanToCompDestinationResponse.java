package HPScan2Linux.HPScan2Linux.events;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import HPScan2Linux.HPScan2Linux.StateService;

public final class WalkupScanToCompDestinationResponse extends ResponseExecutor
{

    @Override
    public void execute()
    {
        StateService.getInstance().setProfile(m_scanProfile);
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

            m_hostname = getXMLParam(doc, "Hostname", "http://www.hp.com/schemas/imaging/con/dictionaries/1.0/");
            if (m_hostname == null)
                m_hostname = getXMLParam(doc, "Hostname",
                        "http://www.hp.com/schemas/imaging/con/dictionaries/2009/04/06");

            m_scanProfile = getXMLParam(doc, "Shortcut",
                    "http://www.hp.com/schemas/imaging/con/ledm/walkupscan/2010/09/28");
            m_resourceURI = getXMLParam(doc, "ResourceURI", "http://www.hp.com/schemas/imaging/con/dictionaries/1.0/");

        }
        catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        }
        catch (SAXException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String getResourceURI()
    {
        return m_resourceURI;
    }

    public String getHostName()
    {
        return m_hostname;
    }

    private String m_hostname;
    private String m_scanProfile;
    private String m_resourceURI;
}
