package com.dara.hpscan.internal.events.jobs;

import com.dara.hpscan.StateService;
import org.apache.http.Header;
import org.apache.http.HttpResponse;

import java.net.URI;
import java.net.URISyntaxException;

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
                    catch (URISyntaxException e)
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
