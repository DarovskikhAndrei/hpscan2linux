package com.dara.hpscan.internal.events.scanstatus;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.dara.hpscan.internal.ResponseExecutorHelper;

/**
 * Состояние сканирование. Данные ответа
 */
public final class ScanStatusResponse
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ScanStatusResponse.class);

    private final String scanStatus;

    public static ScanStatusResponse create(HttpResponse response)
    {
        try(InputStream inStream = ResponseExecutorHelper.getBodyStream(response))
        {
            Document doc = ResponseExecutorHelper.getXMLDocument(inStream);

            final String scanStatus = ResponseExecutorHelper.getXMLParam(doc, "ScannerState",
                    "http://www.hp.com/schemas/imaging/con/cnx/scan/2008/08/19");
            LOGGER.info("Scaner status: {}", scanStatus);

            return new ScanStatusResponse(scanStatus);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage());
        }

        return null;
    }

    private ScanStatusResponse(String scanStatus)
    {
        this.scanStatus = scanStatus;
    }

    public boolean idle()
    {
        return scanStatus.equalsIgnoreCase("Idle");
    }
}