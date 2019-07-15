package HPScan2Linux.HPScan2Linux.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import HPScan2Linux.HPScan2Linux.RequestHelper;

public class EventFactory
{
    protected static final Logger LOGGER = LoggerFactory.getLogger(EventFactory.class);
    
    public static Event createEvent(String resourceURI)
    {
        return new Event(resourceURI,
                         "",
                         getMethod(resourceURI, ""),
                         getResponseExecutor(resourceURI, ""),
                         null);
    }

    public static Event createEvent(String resourceURI, String resourceType)
    {
        return new Event(resourceURI,
                         resourceType,
                         getMethod(resourceURI, resourceType),
                         getResponseExecutor(resourceURI, resourceType),
                         null);
    }

    public static Event createEvent(String resourceURI, RequestHelper requestHelper)
    {
        return new Event(resourceURI,
                         "",
                         "POST",
                         getResponseExecutor(resourceURI, ""),
                         requestHelper);
    }

    public static IEventResultFactory getResponseExecutor(String resourceURI, String resourceType)
    {
        if (resourceURI == null)
        {
            LOGGER.info("Url: {} Event: {}", "null", "EventResponse");
            return null;
        }

        if (resourceURI.equals("/EventMgmt/EventTable") || resourceURI.equals("/EventMgmt/EventTable?timeout=1200"))
        {
            LOGGER.info("Url: {} Event: {}", resourceURI, "EventResponse");
            return new DefaultEventResponseFactory();
        }
        else if (resourceURI.equals("/WalkupScanToComp/WalkupScanToCompDestinations"))
        {
            LOGGER.info("Url: {} Event: {}", resourceURI, "WalkupScanToCompDestinationResponse");
            return new WalkupScanToCompDestinationResponseFactory();
        }
        else if (resourceURI.equals("/WalkupScanToComp/WalkupScanToCompEvent"))
        {
            LOGGER.info("Url: {} Event: {}", resourceURI, "WalkupScanToCompEventResponse");
            return new WalkupScanToCompEventResponseFactory();
        }
        else if (resourceURI.equals("/WalkupScanToComp/WalkupScanToCompCaps"))
        {
            LOGGER.info("Url: {} Event: {}", resourceURI, "WalkupScanToCompCapsResponse");
            return new WalkupScanToCompCapsResponseFactory();
        }

        else if (resourceURI.equals("/DevMgmt/DiscoveryTree.xml"))
        {
            LOGGER.info("Url: {} Event: {}", resourceURI, "null");
            return null;
        }
        else if (resourceURI.equals("/Scan/ScanCaps.xml"))
        {
            LOGGER.info("Url: {} Event: {}", resourceURI, "null");
            return null;
        }
        else if (resourceURI.equals("/Scan/Jobs"))
        {
            LOGGER.info("Url: {} Event: {}", resourceURI, "ScanJobsResponse");
            return new ScanJobsResponseFactory();
        }
        else if (resourceURI.equals("/Scan/Status"))
        {
            LOGGER.info("Url: {} Event: {}", resourceURI, "ScanStatusResponse");
            return new ScanStatusResponseFactory();
        }

        String subStr = resourceURI.substring(0, (new String("/Jobs/JobList/")).length());
        if (subStr.equals("/Jobs/JobList/"))
        {
            LOGGER.info("Url: {} Event: {}", resourceURI, "ScanJobListResponse");
            return new ScanJobListResponseFactory();
        }

        // номер работы тут может меняться.
        // возможно стоит эту задачу создавать в другом месте
        if (resourceURI.equals("/Scan/Jobs/1/Pages"))
        {
            LOGGER.info("Url: {} Event: {}", resourceURI, "GetScanResultResponse");
            return new GetScanResultResponseFactory();
        }
        subStr = resourceURI.substring(0, (new String("/WalkupScanToComp/WalkupScanToCompDestinations")).length());
        if (subStr.equals("/WalkupScanToComp/WalkupScanToCompDestinations"))
        {
            LOGGER.info("Url: {} Event: {}", resourceURI, "WalkupScanToCompDestinationResponse");
            return new WalkupScanToCompDestinationResponseFactory();
        }

        LOGGER.info("Url: {} Event: {}", resourceURI, "null");
        return null;
    }

    public static String getMethod(String resourceURI, String resourceType)
    {
        return "GET";
    }
}
