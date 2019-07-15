package com.dara.hpscan.internal.events;

import java.util.Arrays;
import java.util.List;

import com.dara.hpscan.internal.events.jobs.ScanJobsRequestFactory;
import com.dara.hpscan.internal.events.scanstatus.ScanStatusRequestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dara.hpscan.IRequestBodyProvider;
import com.dara.hpscan.internal.events.caps.WalkupScanToCompCapsRequestFactory;
import com.dara.hpscan.internal.events.compdest.WalkupScanToCompDestinationRequestFactory;
import com.dara.hpscan.internal.events.compevent.WalkupScanToCompEventRequestFactory;
import com.dara.hpscan.internal.events.def.DefaultEventRequestFactory;
import com.dara.hpscan.internal.events.scanresult.GetScanResultRequestFactory;
import com.dara.hpscan.internal.events.joblist.ScanJobListRequestFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class EventFactory
{
    private static final Logger LOGGER = LoggerFactory.getLogger(EventFactory.class);

    /**
     * Фабрики создания событий.
     *
     * Порядок важен. Последним должно быть событие по умолчанию (DefaultEventRequest)
     */
    private static final List<IEventRequestFactory> factories = Arrays.asList(
            WalkupScanToCompDestinationRequestFactory.INSTANCE,
            WalkupScanToCompEventRequestFactory.INSTANCE,
            WalkupScanToCompCapsRequestFactory.INSTANCE,
            GetScanResultRequestFactory.INSTANCE,
            ScanJobListRequestFactory.INSTANCE,
            ScanStatusRequestFactory.INSTANCE,
            ScanJobsRequestFactory.INSTANCE,
            DefaultEventRequestFactory.INSTANCE
    );

    public static IEventRequest createEvent(String resourceURI)
    {
        return createEvent(resourceURI, "", null);
    }

    public static IEventRequest createEvent(String resourceURI, String resourceType, IRequestBodyProvider requestBodyProvider)
    {
        return createEvent(resourceURI, resourceType,
                           requestBodyProvider == null ? "GET" : "POST",
                           requestBodyProvider);
    }

    public static IEventRequest createEvent(String resourceURI, String resourceType, String method, IRequestBodyProvider IRequestBodyProvider)
    {
        for (IEventRequestFactory factory : factories)
        {
            if (factory.aсcept(resourceURI))
                return factory.create(resourceURI, resourceType, method, IRequestBodyProvider);
        }

        throw new IllegalStateException("unknown url");
    }

    public static IEventRequest fromElement(Element elem)
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

        return createEvent(resourceURI, resourceType, null);
    }
}
