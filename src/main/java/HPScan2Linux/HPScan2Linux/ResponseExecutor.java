package HPScan2Linux.HPScan2Linux;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public abstract class ResponseExecutor {
	public ResponseExecutor() {
		m_events = new ArrayList<Event>();
	}
	
	public abstract void init(HttpResponse response);
	
	public abstract void execute();
	
	static public InputStream getBodyStream(HttpResponse response) {
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream inStream = null;
			try {
				inStream = entity.getContent();
			} catch (UnsupportedOperationException | IOException e) {
			}
			if (entity.getContentLength() != 0) {
				return inStream;
			}
		}
		return null;
	}
	
	static public Document getXMLDocument(InputStream inStream) throws ParserConfigurationException, SAXException, IOException {
		if (inStream == null)
			return null;
		
		DocumentBuilder builder;
		DocumentBuilderFactory builderFactory;
		
		builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setIgnoringComments(true);
		builderFactory.setNamespaceAware(true);
		builder = builderFactory.newDocumentBuilder();
		
		return builder.parse(inStream);
	}
	
	
	static public String getXMLParam(Document doc, String tag, String ns) {
		NodeList elems = doc.getElementsByTagNameNS(ns, tag);
		if (elems != null && elems.getLength() > 0)
			return elems.item(0).getTextContent();
		
		return null;
	}
	
	public ArrayList<Event> getEvents() {
		return m_events;
	}
	
	protected ArrayList<Event> m_events;
}
