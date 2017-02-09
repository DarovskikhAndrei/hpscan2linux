package HPScan2Linux.HPScan2Linux;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class EventResponse extends ResponseExecutor {
	
	public String getEventCategory() {
		return m_eventName;
	}
	
	@Override
	public void execute() {
	}
	
	@Override
	public void init(HttpResponse response) {
		InputStream inStream = getBodyStream(response);
		if (inStream == null)
			return;
		
		try {
			Document doc = getXMLDocument(inStream);
			
			NodeList elems =
					doc.getElementsByTagNameNS(	"http://www.hp.com/schemas/imaging/con/dictionaries/1.0/",
												"UnqualifiedEventCategory");
			if (elems.getLength() > 0)
				m_eventName = elems.item(0).getTextContent();
			
			elems = doc.getElementsByTagNameNS(	"http://www.hp.com/schemas/imaging/con/ledm/events/2007/09/16",
												"Payload");
			
			for (int index = 0; index < elems.getLength(); ++index) {
				Element elem = (Element) elems.item(index);
				if (elem != null) {
					m_events.add(new Event(elem));
				}
			}
		} catch (ParserConfigurationException e) {
		} catch (SAXException e) {
		} catch (IOException e) {
		}
	}
	
	private String 				m_eventName;
}