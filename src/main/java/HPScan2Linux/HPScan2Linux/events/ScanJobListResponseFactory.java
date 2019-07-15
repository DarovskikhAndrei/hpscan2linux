package HPScan2Linux.HPScan2Linux.events;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import HPScan2Linux.HPScan2Linux.StateService;

/**
 * Данные запроса готовности результата сканирования
 */
public final class ScanJobListResponseFactory implements IEventResultFactory<ScanJobListResponse>
{
    @Override
    public ScanJobListResponse createData(HttpResponse response)
    {
        return ScanJobListResponse.create(response);
    }

    @Override
    public IResponseAction createExecutor()
    {
        return new ScanJobListResponseAction();
    }
}
