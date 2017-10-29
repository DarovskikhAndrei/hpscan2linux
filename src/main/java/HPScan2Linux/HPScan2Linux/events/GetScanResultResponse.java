package HPScan2Linux.HPScan2Linux.events;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import HPScan2Linux.HPScan2Linux.internal.FileNameResolver;
import HPScan2Linux.HPScan2Linux.internal.FileNameResolverFactory;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import HPScan2Linux.HPScan2Linux.SettingsProvider;
import HPScan2Linux.HPScan2Linux.StateService;

public final class GetScanResultResponse extends ResponseExecutor
{
    private static final int kBlockSize = 8192;
    private static final Logger LOGGER = LoggerFactory.getLogger(EventFactory.class);
    
    @Override
    public void execute()
    {
    }

    @Override
    public void init(HttpResponse response)
    {
        InputStream inStream = getBodyStream(response);
        if (inStream == null)
            return;

        String path = SettingsProvider.getSettings().getPathForScanSave();
        String fileName = getFileName();
        String fullFileName = path + fileName;

        try (FileOutputStream fos = new FileOutputStream(fullFileName))
        {
            int readed;
            int pos = 0;
            byte bytes[] = new byte[kBlockSize];

            while ((readed = inStream.read(bytes, pos, kBlockSize)) > 0)
            {
                fos.write(bytes, pos, readed);
            }

            LOGGER.info("Downloaded: {}", fullFileName);
        }
        catch (FileNotFoundException e)
        {
            LOGGER.error(e.getMessage());
        }
        catch (IOException e)
        {
            LOGGER.error(e.getMessage());
        }
        finally
        {
            StateService.getInstance().setBinaryURL(null);
            StateService.getInstance().setJobURL(null);
            StateService.getInstance().setProfile(null);
        }
    }

    private String getFileName()
    {
        return FileNameResolverFactory.createDefault().getFileName(StateService.getInstance());
    }
}
