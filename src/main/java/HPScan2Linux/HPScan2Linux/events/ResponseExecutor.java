package HPScan2Linux.HPScan2Linux.events;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public final class ResponseExecutor
{
    protected final List<Event> events = new ArrayList<Event>();

    static public InputStream getBodyStream(HttpResponse response) throws IOException
    {
        HttpEntity entity = response.getEntity();
        if (entity == null)
            throw new IllegalStateException();

        return entity.getContent();
    }

    static public Document getXMLDocument(InputStream inStream)
            throws ParserConfigurationException, SAXException, IOException
    {
        if (inStream == null)
            return null;

        DocumentBuilder builder;
        DocumentBuilderFactory builderFactory;

        builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setIgnoringComments(true);
        builderFactory.setNamespaceAware(true);
        builder = builderFactory.newDocumentBuilder();

        return builder.parse(inStream);
    }

    static public String getXMLParam(Document doc, String tag, String ns)
    {
        NodeList elems = doc.getElementsByTagNameNS(ns, tag);
        if (elems != null && elems.getLength() > 0)
            return elems.item(0).getTextContent();

        return null;
    }
}
