package HPScan2Linux.HPScan2Linux;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class ScanStatusResponse extends ResponseExecutor {
	@Override
	public void execute() {
		if (m_scanStatus.equalsIgnoreCase("Idle") && StateService.getInstance().getProfile() != null) {
			m_events.add(EventFactory.createEvent("/Scan/Jobs", new ScanJobsRequest()));
		}
	}
	
	@Override
	public void init(HttpResponse response) {
		InputStream inStream = getBodyStream(response);
		if (inStream == null)
			return;
		
		try {
			Document doc = getXMLDocument(inStream);
			
			m_scanStatus = getXMLParam(doc, "ScannerState", "http://www.hp.com/schemas/imaging/con/cnx/scan/2008/08/19");
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String m_scanStatus;
}