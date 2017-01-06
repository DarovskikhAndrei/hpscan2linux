package HPScan2Linux.HPScan2Linux;

public class EventFactory {
	public static Event createEvent(String resourceURI) {
		return new Event(	resourceURI,
							"",
							getMethod(resourceURI, ""),
							getResponseExecutor(resourceURI, ""),
							null);
	}
	
	public static Event createEvent(String resourceURI, String resourceType) {
		return new Event(	resourceURI,
							resourceType,
							getMethod(resourceURI, resourceType),
							getResponseExecutor(resourceURI, resourceType),
							null);
	}
	
	public static Event createEvent(String resourceURI, RequestHelper requestHelper) {
		return new Event(	resourceURI,
							"",
							"POST",
							getResponseExecutor(resourceURI, ""),
							requestHelper);
	}
	
	public static ResponseExecutor getResponseExecutor(String resourceURI, String resourceType) {
		if (resourceURI == null)
			return null;
		
		if (resourceURI.equals("/EventMgmt/EventTable") || resourceURI.equals("/EventMgmt/EventTable?timeout=1200"))
			return new EventResponse();
		else if (resourceURI.equals("/WalkupScanToComp/WalkupScanToCompDestinations"))
			return new WalkupScanToCompDestinationResponse();
		else if (resourceURI.equals("/WalkupScanToComp/WalkupScanToCompEvent"))
			return new WalkupScanToCompEventResponse();
		else if (resourceURI.equals("/WalkupScanToComp/WalkupScanToCompCaps"))
			return new WalkupScanToCompCapsResponse();

		else if (resourceURI.equals("/DevMgmt/DiscoveryTree.xml"))
			return null;
		else if (resourceURI.equals("/Scan/ScanCaps.xml"))
			return null;
		else if (resourceURI.equals("/Scan/Jobs"))
			return new ScanJobsResponse();
		else if (resourceURI.equals("/Scan/Status"))
			return new ScanStatusResponse();
		
		String subStr = resourceURI.substring(0, (new String("/Jobs/JobList/")).length());
		if (subStr.equals("/Jobs/JobList/"))
			return new ScanJobListResponse();
		
		// номер работы тут может меняться.
		// возможно стоит эту задачу создавать в другом месте
		if (resourceURI.equals("/Scan/Jobs/1/Pages"))
			return new GetScanResultResponse();		
		subStr = resourceURI.substring(0, (new String("/WalkupScanToComp/WalkupScanToCompDestinations")).length());
		if (subStr.equals("/WalkupScanToComp/WalkupScanToCompDestinations"))
			return new WalkupScanToCompDestinationResponse();
		
		return null;
	}
	
	public static String getMethod(String resourceURI, String resourceType) {
		return "GET";	
	}
}
