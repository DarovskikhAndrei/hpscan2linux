package com.dara.hpscan.internal.events.def;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dara.hpscan.IRequestBodyProvider;
import com.dara.hpscan.internal.events.IEventRequest;
import com.dara.hpscan.internal.events.IEventResultFactory;

/**
 * Запрос по умолчанию. Не имеет специальных обработок на сервере.
 */
public class DefaultEventRequest implements IEventRequest
{
    private final String resourceType;
    private final String resourceURI;
    private final String method;

    public static DefaultEventRequest from(Element elem)
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

        return new DefaultEventRequest(resourceURI, resourceType);
    }

    public DefaultEventRequest(String url, String type, String method)
    {
        this.resourceURI = url;
        this.resourceType = type;
        this.method = method;
    }

    public DefaultEventRequest(String url, String type)
    {
        this.resourceURI = url;
        this.resourceType = type;
        this.method = "GET";
    }

    @Override
    public String getResourceURL()
    {
        return resourceURI;
    }

    @Override
    public String getHttpMethod()
    {
        return method;
    }

    @Override
    public IEventResultFactory getEventResultFactory()
    {
        return DefaultEventResponseFactory.INSTANCE;
    }

    @Override
    public IRequestBodyProvider getIRequestBodyProvider()
    {
        return null;
    }
}
