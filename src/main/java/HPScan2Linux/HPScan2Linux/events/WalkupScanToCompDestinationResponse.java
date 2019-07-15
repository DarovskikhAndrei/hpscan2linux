package HPScan2Linux.HPScan2Linux.events;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.w3c.dom.Document;

public final class WalkupScanToCompDestinationResponse
{
    private String hostname;
    private String scanProfile;
    private String resourceURI;

    private WalkupScanToCompDestinationResponse(String hostname, String scanProfile, String resourceURI)
    {
        this.hostname = hostname;
        this.scanProfile = scanProfile;
        this.resourceURI = resourceURI;
    }

    public static WalkupScanToCompDestinationResponse create(HttpResponse response)
    {
        try(InputStream inStream = ResponseExecutor.getBodyStream(response))
        {
            Document doc = ResponseExecutor.getXMLDocument(inStream);

            String hostname = ResponseExecutor.getXMLParam(doc, "Hostname", "http://www.hp.com/schemas/imaging/con/dictionaries/1.0/");
            if (hostname == null)
                hostname = ResponseExecutor.getXMLParam(doc, "Hostname",
                        "http://www.hp.com/schemas/imaging/con/dictionaries/2009/04/06");

            String scanProfile = ResponseExecutor.getXMLParam(doc, "Shortcut",
                    "http://www.hp.com/schemas/imaging/con/ledm/walkupscan/2010/09/28");
            String resourceURI = ResponseExecutor.getXMLParam(doc, "ResourceURI", "http://www.hp.com/schemas/imaging/con/dictionaries/1.0/");

            return new WalkupScanToCompDestinationResponse(hostname, scanProfile, resourceURI);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public String getResourceURI()
    {
        return resourceURI;
    }

    public String getHostName()
    {
        return hostname;
    }
}
