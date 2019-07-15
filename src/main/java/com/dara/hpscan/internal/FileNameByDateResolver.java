package com.dara.hpscan.internal;

import com.dara.hpscan.SettingsProvider;
import com.dara.hpscan.StateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.dara.hpscan.internal.ResponseExecutorHelper.getXMLDocument;
import static com.dara.hpscan.internal.ResponseExecutorHelper.getXMLParam;

public class FileNameByDateResolver implements FileNameResolver
{
    private static final Logger LOGGER = LoggerFactory.getLogger(FileNameByDateResolver.class);

    @Override
    public String getFileName(StateService instance)
    {
        String filename = "scan_" + getCurrentDateTime();
        String ext = getProfileExt(instance);
        filename += "." + ext;

        return filename;
    }

    private String getCurrentDateTime()
    {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");//dd/MM/yyyy
        return sdfDate.format(new Date());
    }

    private String getProfileExt(StateService instance)
    {
        String profiles = SettingsProvider.getSettings().getProfilesPath();
        File file = new File(profiles + instance.getProfile() + ".xml");
        try
        {
            FileInputStream buf = new FileInputStream(file);
            Document doc = getXMLDocument(buf);
            String format = getXMLParam(doc, "Format", "http://www.hp.com/schemas/imaging/con/cnx/scan/2008/08/19");
            buf.close();
            if (format != null)
            {
                if (format.equalsIgnoreCase("Jpeg"))
                    return "jpg";
            }
        }
        catch (IOException | ParserConfigurationException | SAXException e)
        {
            LOGGER.error(e.getMessage());
        }

        LOGGER.error("file exception not valid");
        return "";
    }
}
