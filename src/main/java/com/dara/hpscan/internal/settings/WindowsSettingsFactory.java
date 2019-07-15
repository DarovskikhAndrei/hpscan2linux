package com.dara.hpscan.internal.settings;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.dara.hpscan.ISettings;
import com.dara.hpscan.internal.ResponseExecutorHelper;

public class WindowsSettingsFactory implements ISettingsFactory
{
    private static final Logger LOGGER = LoggerFactory.getLogger(WindowsSettingsFactory.class);

    private static final String CONF_PATH = "c:/scanserver/conf.xml";
    private static final String PROFILES_DEFAULT_PATH = "c:/scanserver/";

    @Override
    public ISettings getSettings()
    {
        File file = new File(CONF_PATH);

        if (!file.exists())
        {
            LOGGER.error("settings file not exists");
            throw new IllegalStateException("settings file not exists");
        }

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

        try
        {
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document doc = builder.parse(file);

            String path2ScanSave;
            String printerAddress;
            int printerPort;
            String profilesPath;
            String proxyHost;
            int proxyPort;

            path2ScanSave = ResponseExecutorHelper.getXMLParam(doc, "scanpath");
            if (path2ScanSave == null)
                LOGGER.error("scanpath not defined. This parametr is required!");

            printerAddress = ResponseExecutorHelper.getXMLParam(doc, "printer_addr");
            if (printerAddress == null)
            {
                LOGGER.debug("printer_addr not defined. Use default");
                printerAddress = "192.168.10.7";
            }

            String buf = ResponseExecutorHelper.getXMLParam(doc, "printer_port");
            if (buf == null)
            {
                LOGGER.debug("printer_addr not defined. Use default");
                printerPort = 8080;
            }
            else
                printerPort = Integer.parseInt(buf);

            profilesPath = ResponseExecutorHelper.getXMLParam(doc, "profiles");
            if (profilesPath == null)
            {
                LOGGER.debug("profiles folder not defined. Use default");
                profilesPath = PROFILES_DEFAULT_PATH;
            }

            proxyHost = ResponseExecutorHelper.getXMLParam(doc, "proxyHost");
            buf = ResponseExecutorHelper.getXMLParam(doc, "proxyPort");
            if (buf != null)
                proxyPort = Integer.parseInt(buf);
            else if (proxyHost == null || proxyHost.isEmpty())
                proxyPort = 0;
            else
                throw new IllegalStateException("proxy config invalid");

            return new Settings(path2ScanSave, printerAddress, printerPort, profilesPath, proxyHost, proxyPort);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
