package HPScan2Linux.HPScan2Linux.events;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import HPScan2Linux.HPScan2Linux.RequestHelper;

public class Event implements IEvent
{
    private final String resourceType;
    private final String resourceURI;
    private final String httpMethod;
    private final IEventResultFactory eventResultFactory;
    private final RequestHelper requestHelper;

    public static Event from(Element elem)
    {
        final String httpMethod;
        final IEventResultFactory eventResultFactory;
        String resourceType = "";
        String resourceURI = "";

        NodeList childs = elem.getChildNodes();
        for (int childIndex = 0; childIndex < childs.getLength(); ++childIndex)
        {
            Node node = childs.item(childIndex);
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element childElem = (Element) node;
                if (childElem != null)
                {
                    if (childElem.getLocalName().equalsIgnoreCase("ResourceType"))
                    {
                        resourceType = childElem.getTextContent();
                    }
                    else if (childElem.getLocalName().equalsIgnoreCase("ResourceURI"))
                    {
                        resourceURI = childElem.getTextContent();
                    }
                }
            }
        }

        httpMethod = EventFactory.getMethod(resourceURI, resourceType);
        eventResultFactory = EventFactory.getResponseExecutor(resourceURI, resourceType);

        return new Event(resourceURI, resourceType, httpMethod, eventResultFactory, null);
    }

    public Event(String url, String type, String method, IEventResultFactory eventResultFactory, RequestHelper requestHelper)
    {
        this.resourceURI = url;
        this.resourceType = type;
        this.httpMethod = method;
        this.eventResultFactory = eventResultFactory;
        this.requestHelper = requestHelper;
    }

    public String getResourceType()
    {
        return resourceType;
    }

    public String getResourceURL()
    {
        return resourceURI;
    }

    public String getHttpMethod()
    {
        return httpMethod;
    }

    public IEventResultFactory getEventResultFactory()
    {
        return eventResultFactory;
    }

    public RequestHelper getRequestHelper()
    {
        return requestHelper;
    }
}
