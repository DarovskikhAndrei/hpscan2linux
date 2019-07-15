package HPScan2Linux.HPScan2Linux.events;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import com.sun.org.apache.xerces.internal.util.URI;
import com.sun.org.apache.xerces.internal.util.URI.MalformedURIException;

import HPScan2Linux.HPScan2Linux.StateService;

/**
 * Данные ответа на задачу сканирования
 */
public final class ScanJobsResponse
{
    private final String url;

    public static ScanJobsResponse create(HttpResponse response)
    {
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
                        final String uriPath = uri.getPath();

                        // TODO. Надо от этого избавиться
                        StateService.getInstance().setJobURL(uriPath);

                        return new ScanJobsResponse(uriPath);

                    }
                    catch (MalformedURIException e)
                    {
                        e.printStackTrace();
                    }

                    break;
                }
            }
        }

        return null;
    }

    private ScanJobsResponse(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }
}
