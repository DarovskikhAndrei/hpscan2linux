package com.dara.hpscan.internal.events;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dara.hpscan.IRequestBodyProvider;
import com.dara.hpscan.internal.events.caps.WalkupScanToCompCapsRequestFactory;
import com.dara.hpscan.internal.events.compdest.WalkupScanToCompDestinationRequestFactory;
import com.dara.hpscan.internal.events.compevent.WalkupScanToCompEventRequestFactory;
import com.dara.hpscan.internal.events.def.DefaultEventRequestFactory;
import com.dara.hpscan.internal.events.scanresult.GetScanResultRequestFactory;
import com.dara.hpscan.internal.events.joblist.ScanJobListRequestFactory;

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
            DefaultEventRequestFactory.INSTANCE
    );

    public static IEventRequest createEvent(String resourceURI)
    {
        return createEvent(resourceURI, "", null);
    }

    public static IEventRequest createEvent(String resourceURI, String resourceType, IRequestBodyProvider IRequestBodyProvider)
    {
        for (IEventRequestFactory factory : factories)
        {
            if (factory.acept(resourceURI))
                return factory.create(resourceURI, resourceType, IRequestBodyProvider);
        }

        throw new IllegalStateException("unknown url");
    }

    public static IEventRequest createEvent(String resourceURI, String resourceType)
    {
        return createEvent(resourceURI, resourceType, null);
    }
}
