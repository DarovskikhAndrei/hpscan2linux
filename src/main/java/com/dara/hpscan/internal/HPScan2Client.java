package com.dara.hpscan.internal;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.dara.hpscan.IRequestBodyProvider;
import com.dara.hpscan.SettingsProvider;
import com.dara.hpscan.internal.events.EventFactory;
import com.dara.hpscan.internal.events.IEventRequest;
import com.dara.hpscan.internal.events.IEventResultFactory;
import com.dara.hpscan.internal.events.compdest.RegisterRequestBuilder;
import com.dara.hpscan.internal.events.compdest.WalkupScanToCompDestinationResponse;
import com.dara.hpscan.internal.events.def.DefaultEventRequest;

public class HPScan2Client
{
    private CloseableHttpClient httpClient;

    private final Queue<IEventRequest> requestQueue = new LinkedList<>();

    private String registredURL;

    private String proxyHost;
    private int proxyPort;

    private HttpHost host;

    private void do_init()
    {
        System.err.println("init scaner");

        while (registredURL != null)
        {
            try
            {
                executeEvent(EventFactory.createEvent("/WalkupScanToComp/WalkupScanToCompDestinations"));
                executeEvent(new DefaultEventRequest(registredURL, "", "DELETE"));
                registredURL = null;
                System.err.println("Deleted registration");
            }
            catch (ClientProtocolException e)
            {
            }
            catch (IOException e)
            {
            }
        }

        // Сразу добавим событие для обработки сообщений
        registredURL = null;
        while (registredURL == null)
        {
            try
            {
                executeEvent(EventFactory.createEvent("/WalkupScanToComp/WalkupScanToCompDestinations",
                        "", new RegisterRequestBuilder()));
                executeEvent(EventFactory.createEvent("/WalkupScanToComp/WalkupScanToCompDestinations"));
            }
            catch (ClientProtocolException e)
            {
                try
                {
                    Thread.sleep(3000);
                }
                catch (InterruptedException e1)
                {
                }
            }
            catch (IOException e)
            {
                try
                {
                    Thread.sleep(3000);
                }
                catch (InterruptedException e1)
                {
                }
            }
        }
        System.err.println("Registred");

        // Сразу добавим событие для обработки сообщений
        requestQueue.add(EventFactory.createEvent("/EventMgmt/EventTable"));
    }

    /* инициализация клиента */
    public void init(String hostName, int port)
    {
        httpClient = HttpClients.custom().disableAutomaticRetries().disableRedirectHandling()
                .disableCookieManagement().build();

        host = new HttpHost(hostName, port, "http");

        proxyHost = SettingsProvider.getSettings().getProxyHost();
        proxyPort = SettingsProvider.getSettings().getProxyPort();

        do_init();
    }

    /* закрытие клиента */
    public void close() throws IOException
    {
        // удаляем регистрацию
        if (registredURL != null)
        {
            System.err.println("close execute");
            executeEvent(new DefaultEventRequest(registredURL, "", "DELETE"));
        }

        httpClient.close();
    }

    public void process() throws IOException
    {
        try
        {
            executeEvent(requestQueue.poll());
        }
        catch (SocketTimeoutException e)
        {
            System.err.println("timeout exception");
            do_init();
        }
        catch (HttpHostConnectException e)
        {
            System.err.println("HttpHostConnectException exception");
            do_init();
        }
        catch (ClientProtocolException e)
        {
            System.err.println("ClientProtocolException exception");
            do_init();
        }
        catch (IOException e)
        {
            System.err.println("IOException exception");
            do_init();
        }

        if (requestQueue.isEmpty())
        {
            try
            {
                Thread.sleep(1200);
            }
            catch (InterruptedException e)
            {
            }
            requestQueue.add(EventFactory.createEvent("/EventMgmt/EventTable"));
        }
    }

    private void executeResponse(IEventRequest event, CloseableHttpResponse response)
            throws IOException
    {
        if (response.getStatusLine().getStatusCode() < 400)
        {
            IEventResultFactory factory = event.getEventResultFactory();
            if (factory != null)
            {
                // инициализируем
                Object data = factory.createData(response);

                if (data instanceof WalkupScanToCompDestinationResponse)
                {
                    WalkupScanToCompDestinationResponse wstcdResponse = (WalkupScanToCompDestinationResponse) data;

                    if (wstcdResponse.getHostName() != null
                            && wstcdResponse.getHostName().equalsIgnoreCase(InetAddress.getLocalHost().getHostName()))
                    {
                        if (wstcdResponse.getResourceURI() != null)
                        {
                            registredURL = wstcdResponse.getResourceURI();
                        }
                    }
                }
                // обработаем
                List<IEventRequest> events = factory.createExecutor().execute(data);

                // при необходимости наполним очередь запросов
                for (IEventRequest event2 : events)
                {
                    requestQueue.add(event2);
                }
            }
        }
    }

    private void executeEvent(IEventRequest event) throws ClientProtocolException, IOException
    {
        if (event.getHttpMethod().equals("GET"))
        {
            HttpGet getRequest = new HttpGet(event.getResourceURL());
            if (proxyHost != null)
            {
                HttpHost proxy = new HttpHost(proxyHost, proxyPort, "http");
                RequestConfig cfg = RequestConfig.custom().setProxy(proxy).build();
                getRequest.setConfig(cfg);
            }
            CloseableHttpResponse response = httpClient.execute(host, getRequest);
            try
            {
                executeResponse(event, response);
            }
            finally
            {
                response.close();
            }
        }
        else if (event.getHttpMethod().equals("POST"))
        {
            IRequestBodyProvider helper = event.getIRequestBodyProvider();
            HttpPost postRequest = new HttpPost(event.getResourceURL());
            if (proxyHost != null)
            {
                HttpHost proxy = new HttpHost(proxyHost, proxyPort, "http");
                RequestConfig cfg = RequestConfig.custom().setProxy(proxy).build();
                postRequest.setConfig(cfg);
            }
            HttpEntity entity = new StringEntity(helper.getBody(), ContentType.TEXT_XML);
            postRequest.setEntity(entity);

            try(CloseableHttpResponse response = httpClient.execute(host, postRequest))
            {
                executeResponse(event, response);
            }
        }
        else if (event.getHttpMethod().equals("DELETE"))
        {
            HttpDelete deleteRequest = new HttpDelete(event.getResourceURL());
            if (proxyHost != null)
            {
                HttpHost proxy = new HttpHost(proxyHost, proxyPort, "http");
                RequestConfig cfg = RequestConfig.custom().setProxy(proxy).build();
                deleteRequest.setConfig(cfg);
            }

            try(CloseableHttpResponse response = httpClient.execute(host, deleteRequest))
            {
                executeResponse(event, response);
            }
        }
    }
}
