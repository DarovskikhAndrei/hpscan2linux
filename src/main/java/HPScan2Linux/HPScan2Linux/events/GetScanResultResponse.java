package HPScan2Linux.HPScan2Linux.events;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import HPScan2Linux.HPScan2Linux.SettingsProvider;
import HPScan2Linux.HPScan2Linux.StateService;
import HPScan2Linux.HPScan2Linux.internal.FileNameResolverFactory;

/**
 * Результат сканирования
 */
public final class GetScanResultResponse
{
    private static final int kBlockSize = 8192;
    private static final Logger LOGGER = LoggerFactory.getLogger(EventFactory.class);
    
    public static GetScanResultResponse create(HttpResponse response)
    {
        String path = SettingsProvider.getSettings().getPathForScanSave();
        String fileName = getFileName();
        String fullFileName = path + fileName;

        try(InputStream inStream = ResponseExecutor.getBodyStream(response);
                FileOutputStream fos = new FileOutputStream(fullFileName))
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

        return null;
    }

    private static String getFileName()
    {
        return FileNameResolverFactory.createDefault().getFileName(StateService.getInstance());
    }
}
