package HPScan2Linux.HPScan2Linux.internal;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import HPScan2Linux.HPScan2Linux.ISettings;
import HPScan2Linux.HPScan2Linux.events.ResponseExecutor;

public final class LinuxSettingsFactory implements ISettingsFactory
{
    private static final Logger LOGGER = LoggerFactory.getLogger(LinuxSettingsFactory.class);

    private static final String CONF_PATH = "/etc/scanserver/conf.xml";
    private static final String PROFILES_DEFAULT_PATH = "/etc/scanserver/profiles/";

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

            path2ScanSave = ResponseExecutor.getXMLParam(doc, "scanpath");
            if (path2ScanSave == null)
                LOGGER.error("scanpath not defined. This parametr is required!");

            printerAddress = ResponseExecutor.getXMLParam(doc, "printer_addr");
            if (printerAddress == null)
            {
                LOGGER.debug("printer_addr not defined. Use default");
                printerAddress = "192.168.10.7";
            }

            String buf = ResponseExecutor.getXMLParam(doc, "printer_port");
            if (buf == null)
            {
                LOGGER.debug("printer_addr not defined. Use default");
                printerPort = 8080;
            }
            else
                printerPort = Integer.parseInt(buf);

            profilesPath = ResponseExecutor.getXMLParam(doc, "profiles");
            if (profilesPath == null)
            {
                LOGGER.debug("profiles folder not defined. Use default");
                profilesPath = PROFILES_DEFAULT_PATH;
            }

            proxyHost = ResponseExecutor.getXMLParam(doc, "proxyHost");
            buf = ResponseExecutor.getXMLParam(doc, "proxyPort");
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
