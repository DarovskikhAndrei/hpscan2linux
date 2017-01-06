package HPScan2Linux.HPScan2Linux;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Event {
	public Event(String url, String type, String method, ResponseExecutor responseExecutor, RequestHelper requestHelper) {
		m_resourceURI = url;
		m_resourceType = type;
		m_httpMethod = method;
		m_responseExecutor = responseExecutor;
		m_requestHelper = requestHelper;
	}
	
	public Event(Element elem) {
		NodeList childs = elem.getChildNodes();
		for (int childIndex = 0; childIndex < childs.getLength(); ++childIndex) {
			Node node = childs.item(childIndex);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element childElem = (Element) node;
				if (childElem != null) {
					if (childElem.getLocalName().equalsIgnoreCase("ResourceType"))
						m_resourceType = childElem.getTextContent(); 
					else if (childElem.getLocalName().equalsIgnoreCase("ResourceURI"))
						m_resourceURI = childElem.getTextContent();
				}
			}
		}
		
		if (m_resourceURI != null) {
			m_httpMethod = EventFactory.getMethod(m_resourceURI, m_resourceType);
			m_responseExecutor = EventFactory.getResponseExecutor(m_resourceURI, m_resourceType);
			m_requestHelper = null;
		}
	}
	
	public String getResourceType() {
		return m_resourceType;
	}
	
	public String getResourceURI() {
		return m_resourceURI;
	}
	
	public String getHttpMethod() {
		return m_httpMethod;
	}
	
	public ResponseExecutor getResponseExecutor() {
		return m_responseExecutor;
	}
	
	public RequestHelper getRequestHelper() {
		return m_requestHelper;
	}
	
	private String m_resourceType;
	private String m_resourceURI;
	private String m_httpMethod;
	private ResponseExecutor m_responseExecutor;
	private RequestHelper m_requestHelper;
}
