package HPScan2Linux.HPScan2Linux;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
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


public class HPScan2Client {
	public HPScan2Client() {
		m_requestQueue = new LinkedList<Event>();
	}

	private void do_init() {
		System.err.println("init scaner");
		
		while (m_registredURL != null) {
			try {
				executeEvent(EventFactory.createEvent("/WalkupScanToComp/WalkupScanToCompDestinations"));
				executeEvent(new Event(m_registredURL, "", "DELETE", null, null));
				m_registredURL = null;
				System.err.println("Deleted registration");
			} catch (ClientProtocolException e) {
			} catch (IOException e) {
			}
		}
		
		// Сразу добавим событие для обработки сообщений
		m_registredURL = null;
		while (m_registredURL == null) {
			try {
				executeEvent(EventFactory.createEvent("/WalkupScanToComp/WalkupScanToCompDestinations", new RegisterRequestBuilder()));
				executeEvent(EventFactory.createEvent("/WalkupScanToComp/WalkupScanToCompDestinations"));
			} catch (ClientProtocolException e) {
				System.err.println("ClientProtocolException exception in registration");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
				}
			} catch (IOException e) {
				System.err.println("IOException exception in registration");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
				}
			}
		}
		System.err.println("Registred");
		
		// Сразу добавим событие для обработки сообщений
		m_requestQueue.add(EventFactory.createEvent("/EventMgmt/EventTable"));
	}
	/* инициализация клиента */
	public void init(String hostName, int port)
	{
		m_httpClient = HttpClients.custom()
							.disableAutomaticRetries()
							.disableRedirectHandling()
							.disableCookieManagement()
							.build();
		
		m_host = new HttpHost(hostName, port, "http");
		
		m_proxyHost = SettingsProvider.getSettings().getProxyHost();
		m_proxyPort = SettingsProvider.getSettings().getProxyPort();
		
		do_init();
	}
	
	/* закрытие клиента */
	public void close() throws IOException
	{
		// удаляем регистрацию
		if (m_registredURL != null) {
			System.err.println("close execute");
			executeEvent(new Event(m_registredURL, "", "DELETE", null, null));
		}
		
		m_httpClient.close();
	}
	
	public void process() throws IOException
	{
		try {
			executeEvent(m_requestQueue.poll());
		} catch (SocketTimeoutException e) {
			System.err.println("timeout exception");
			do_init();
		} catch (HttpHostConnectException e) {
			System.err.println("HttpHostConnectException exception");
			do_init();
		} catch (ClientProtocolException e) {
			System.err.println("ClientProtocolException exception");
			do_init();
		} catch (IOException e) {
			System.err.println("IOException exception");
			do_init();
		}
		
		if (m_requestQueue.isEmpty()) {
			try {
				Thread.sleep(1200);
			} catch (InterruptedException e) {
			}
			m_requestQueue.add(EventFactory.createEvent("/EventMgmt/EventTable"));
		}
	}
	
	private void executeResponse(Event event, CloseableHttpResponse response) throws ClientProtocolException, IOException {
		if (response.getStatusLine().getStatusCode() < 400) {  
			ResponseExecutor responseExecutor = event.getResponseExecutor();
			if (responseExecutor != null) {
				// инициализируем
				responseExecutor.init(response);
						
				if (responseExecutor instanceof WalkupScanToCompDestinationResponse) {
					WalkupScanToCompDestinationResponse wstcdResponse = (WalkupScanToCompDestinationResponse) responseExecutor;
						
					if (wstcdResponse.getHostName() != null && wstcdResponse.getHostName().equalsIgnoreCase(InetAddress.getLocalHost().getHostName())) {
						if (wstcdResponse.getResourceURI() != null) {
							m_registredURL = wstcdResponse.getResourceURI();
						}
					}
				}
				// обработаем
				responseExecutor.execute();
				
				// при необходимости наполним очередь запросов
				for (Event event2 : responseExecutor.getEvents()) {
					m_requestQueue.add(event2);
				}				
			}					
		}
	}
	
	private void executeEvent(Event event) throws ClientProtocolException, IOException {
		if (event.getHttpMethod().equals("GET")) {
			HttpGet getRequest = new HttpGet(event.getResourceURI());
			if (m_proxyHost != null) {
				HttpHost proxy = new HttpHost(m_proxyHost, m_proxyPort, "http");
				RequestConfig cfg = RequestConfig.custom()
						.setProxy(proxy)
						.build();
				getRequest.setConfig(cfg);
			}
			CloseableHttpResponse response = m_httpClient.execute(m_host, getRequest);
			executeResponse(event, response);
		}
		else if (event.getHttpMethod().equals("POST")) {
			RequestHelper helper = event.getRequestHelper();
			HttpPost postRequest = new HttpPost(event.getResourceURI());
			if (m_proxyHost != null) {
				HttpHost proxy = new HttpHost(m_proxyHost, m_proxyPort, "http");
				RequestConfig cfg = RequestConfig.custom()
						.setProxy(proxy)
						.build();
				postRequest.setConfig(cfg); 
			}
			HttpEntity entity = new StringEntity(helper.getBody(), ContentType.TEXT_XML);
			postRequest.setEntity(entity);
			
			CloseableHttpResponse response = m_httpClient.execute(m_host, postRequest);
			
			executeResponse(event, response);
		}
		else if (event.getHttpMethod().equals("DELETE")) {
			HttpDelete deleteRequest = new HttpDelete(event.getResourceURI());
			if (m_proxyHost != null) {
				HttpHost proxy = new HttpHost(m_proxyHost, m_proxyPort, "http");
				RequestConfig cfg = RequestConfig.custom()
						.setProxy(proxy)
						.build();
				deleteRequest.setConfig(cfg); 
			}
			CloseableHttpResponse response = m_httpClient.execute(m_host, deleteRequest);
			executeResponse(event, response);
		}
	}
	
	private CloseableHttpClient m_httpClient;
	
	private String m_registredURL;
	
	private String m_proxyHost;
	private int m_proxyPort;
	
	private HttpHost m_host;

	// Очередь запросов.
	private Queue<Event> m_requestQueue;
}
