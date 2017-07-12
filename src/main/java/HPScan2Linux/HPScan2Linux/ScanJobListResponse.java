package HPScan2Linux.HPScan2Linux;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class ScanJobListResponse extends ResponseExecutor {
	@Override
	public void execute() {
		// если задание не выполнено.. повторим запрос.
		
		if (m_jobPageState != null && m_jobPageState.equalsIgnoreCase("ReadyToUpload"))
		{
            System.err.println("Add download event with url: " + m_jobBinaryURL);
			m_events.add(new Event(m_jobBinaryURL, "", "GET", new GetScanResultResponse(), null));
		}
		else if (m_jobState != null && m_jobState.equalsIgnoreCase("canceled")) {
			m_events.add(EventFactory.createEvent("/EventMgmt/EventTable"));	
		}
		else {
			m_events.add(EventFactory.createEvent(m_jobURL));
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}
	}
	
	@Override
	public void init(HttpResponse response) {
		InputStream inStream = getBodyStream(response);
		if (inStream == null)
			return;
		
		try {
			Document doc = getXMLDocument(inStream);
			
			m_jobURL = getXMLParam(doc, "JobUrl", "http://www.hp.com/schemas/imaging/con/ledm/jobs/2009/04/30");
			m_jobCategory = getXMLParam(doc, "JobCategory", "http://www.hp.com/schemas/imaging/con/ledm/jobs/2009/04/30");
			m_jobState = getXMLParam(doc, "JobState", "http://www.hp.com/schemas/imaging/con/ledm/jobs/2009/04/30");
			m_jobSource = getXMLParam(doc, "JobSource", "http://www.hp.com/schemas/imaging/con/ledm/jobs/2009/04/30");
			m_jobPageState = getXMLParam(doc, "PageState", "http://www.hp.com/schemas/imaging/con/cnx/scan/2008/08/19");
			m_jobBinaryURL = getXMLParam(doc, "BinaryURL", "http://www.hp.com/schemas/imaging/con/cnx/scan/2008/08/19");
			StateService.getInstance().setBinaryURL(m_jobBinaryURL);
			
			System.err.println("JobList result start:");
			System.err.println("  Page state: " + m_jobPageState);
			System.err.println("  BinaryURL: " + m_jobBinaryURL);
			System.err.println("JobList result end");
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getJobURL() {
		return m_jobURL;
	}
	
	public String getJobCategory() {
		return m_jobCategory;
	}

	public String getJobState() {
		return m_jobState;
	}
	
	public String getJobSource() {
		return m_jobSource;
	}
	
	public String getJobPageState() {
		return m_jobPageState;
	}
	
	public String getJobBinaryURL() {
		return m_jobBinaryURL;
	}
	
	private String m_jobURL;
	private String m_jobCategory;
	private String m_jobState;
	private String m_jobSource;
	private String m_jobPageState;
	private String m_jobBinaryURL;
}
