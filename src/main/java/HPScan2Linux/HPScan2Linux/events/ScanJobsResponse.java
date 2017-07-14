package HPScan2Linux.HPScan2Linux.events;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import com.sun.org.apache.xerces.internal.util.URI;
import com.sun.org.apache.xerces.internal.util.URI.MalformedURIException;

import HPScan2Linux.HPScan2Linux.StateService;

public final class ScanJobsResponse extends ResponseExecutor
{

    @Override
    public void init(HttpResponse response)
    {
        m_url = null;
        if (response != null)
        {
            Header[] headers = response.getAllHeaders();
            for (Header header : headers)
            {
                if (header.getName().equalsIgnoreCase("Location"))
                {
                    String url = header.getValue();
                    URI uri;
                    try
                    {
                        uri = new URI(url);
                        if (uri != null)
                            m_url = uri.getPath();
                        StateService.getInstance().setJobURL(m_url);

                    }
                    catch (MalformedURIException e)
                    {
                        e.printStackTrace();
                    }

                    break;
                }
            }
        }
    }

    @Override
    public void execute()
    {
        if (m_url != null)
            m_events.add(EventFactory.createEvent(m_url));
    }

    private String m_url;
}
