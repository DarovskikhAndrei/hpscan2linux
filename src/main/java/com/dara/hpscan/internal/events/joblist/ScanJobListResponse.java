package com.dara.hpscan.internal.events.joblist;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.dara.hpscan.StateService;
import com.dara.hpscan.internal.ResponseExecutorHelper;
import com.dara.hpscan.internal.events.EventFactory;

/**
 * Данные запроса готовности результата сканирования
 */
public final class ScanJobListResponse
{
    private static final Logger LOGGER = LoggerFactory.getLogger(EventFactory.class);

    private final String jobURL;
    private final String jobCategory;
    private final String jobState;
    private final String jobSource;
    private final String jobPageState;
    private final String jobBinaryURL;

    public static ScanJobListResponse create(HttpResponse response)
    {
        try(InputStream inStream = ResponseExecutorHelper.getBodyStream(response))
        {
            Document doc = ResponseExecutorHelper.getXMLDocument(inStream);

            final String jobURL = ResponseExecutorHelper.getXMLParam(doc, "JobUrl", "http://www.hp.com/schemas/imaging/con/ledm/jobs/2009/04/30");
            final String jobCategory = ResponseExecutorHelper.getXMLParam(doc, "JobCategory",
                    "http://www.hp.com/schemas/imaging/con/ledm/jobs/2009/04/30");
            final String jobState = ResponseExecutorHelper.getXMLParam(doc, "JobState", "http://www.hp.com/schemas/imaging/con/ledm/jobs/2009/04/30");
            final String jobSource = ResponseExecutorHelper.getXMLParam(doc, "JobSource", "http://www.hp.com/schemas/imaging/con/ledm/jobs/2009/04/30");
            final String jobPageState = ResponseExecutorHelper.getXMLParam(doc, "PageState", "http://www.hp.com/schemas/imaging/con/cnx/scan/2008/08/19");
            final String jobBinaryURL = ResponseExecutorHelper.getXMLParam(doc, "BinaryURL", "http://www.hp.com/schemas/imaging/con/cnx/scan/2008/08/19");

            LOGGER.info("JobList result:\n  {}\n  {}", jobPageState, jobBinaryURL);

            // TODO. Надо избавиться от этого состояния
            StateService.getInstance().setBinaryURL(jobBinaryURL);

            return new ScanJobListResponse(jobURL, jobCategory, jobState, jobSource, jobPageState, jobBinaryURL);
        }
        catch (ParserConfigurationException e)
        {
            LOGGER.error(e.getMessage());
        }
        catch (SAXException e)
        {
            LOGGER.error(e.getMessage());
        }
        catch (IOException e)
        {
            LOGGER.error(e.getMessage());
        }

        return null;
    }

    private ScanJobListResponse(String jobURL, String jobCategory, String jobState, String jobSource,
                                String jobPageState, String jobBinaryURL)
    {
        this.jobURL = jobURL;
        this.jobCategory = jobCategory;
        this.jobState = jobState;
        this.jobSource = jobSource;
        this.jobPageState = jobPageState;
        this.jobBinaryURL = jobBinaryURL;
    }

    public boolean readyToUpload()
    {
        return jobPageState != null && jobPageState.equalsIgnoreCase("ReadyToUpload");
    }

    public boolean canceled()
    {
        return jobState != null || jobState.equalsIgnoreCase("canceled");
    }

    public String getJobURL()
    {
        return jobURL;
    }

    public String getJobCategory()
    {
        return jobCategory;
    }

    public String getJobSource()
    {
        return jobSource;
    }

    public String getJobBinaryURL()
    {
        return jobBinaryURL;
    }
}
