package HPScan2Linux.HPScan2Linux;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public final class SettingsProvider
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsProvider.class);

    static public SettingsProvider getSettings()
    {
        return new SettingsProvider().init();
    }

    public static boolean isWindows()
    {
        String os = System.getProperty("os.name").toLowerCase();
        // windows
        return (os.indexOf("win") >= 0);

    }

    public SettingsProvider init()
    {
        String settingsPath;
        if (isWindows())
            settingsPath = "c:/scanserver/conf.xml";
        else
            settingsPath = "/etc/scanserver/conf.xml";

        File file = new File(settingsPath);

        if (!file.exists())
        {
            LOGGER.error("settings file not exists");

            return this;
        }

        DocumentBuilder builder;
        DocumentBuilderFactory builderFactory;

        try
        {
            builderFactory = DocumentBuilderFactory.newInstance();
            builder = builderFactory.newDocumentBuilder();
            Document doc = builder.parse(file);

            m_path2ScanSave = getXMLParam(doc, "scanpath");
            if (m_path2ScanSave == null)
                LOGGER.error("scanpath not defined. This parametr is required!");

            m_printerAddress = getXMLParam(doc, "printer_addr");
            if (m_printerAddress == null)
            {
                LOGGER.debug("printer_addr not defined. Use default");
                m_printerAddress = "192.168.10.7";
            }

            String buf = getXMLParam(doc, "printer_port");
            if (buf == null)
            {
                LOGGER.debug("printer_addr not defined. Use default");
                m_printerPort = 8080;
            }
            else
                m_printerPort = Integer.parseInt(buf);

            m_profilesPath = getXMLParam(doc, "profiles");
            if (m_profilesPath == null)
            {
                LOGGER.debug("profiles folder not defined. Use default");
                m_profilesPath = "/etc/scanserver/profiles/";
            }

            m_proxyHost = getXMLParam(doc, "proxyHost");
            buf = getXMLParam(doc, "proxyPort");
            if (buf != null)
                m_proxyPort = Integer.parseInt(buf);
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

        return this;
    }

    public String getPathForScanSave()
    {
        return m_path2ScanSave;
    }

    public String getPrinterAddr()
    {
        return m_printerAddress;
    }

    public int getPrinterPort()
    {
        return m_printerPort;
    }

    public String getProfilesPath()
    {
        return m_profilesPath;
    }

    public String getProxyHost()
    {
        return m_proxyHost;
    }

    public int getProxyPort()
    {
        return m_proxyPort;
    }

    static public String getXMLParam(Document doc, String tag)
    {
        NodeList elems = doc.getElementsByTagName(tag);
        if (elems != null && elems.getLength() > 0)
            return elems.item(0).getTextContent();

        return null;
    }

    private String m_path2ScanSave;
    private String m_printerAddress;
    private String m_profilesPath;
    private String m_proxyHost;
    private int m_printerPort;
    private int m_proxyPort;

}