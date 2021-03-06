package com.dara.hpscan.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.dara.hpscan.internal.events.IEventRequest;

/**
 * Помощьник для обработки XML ответа от сканера
 */
public final class ResponseExecutorHelper
{
    protected final List<IEventRequest> events = new ArrayList<>();

    public static InputStream getBodyStream(HttpResponse response) throws IOException
    {
        HttpEntity entity = response.getEntity();
        if (entity == null)
            throw new IllegalStateException();

        return entity.getContent();
    }

    public static Document getXMLDocument(InputStream inStream)
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

        Document document = builder.parse(inStream);

        System.err.println(getDocumentAsString(document));

        return document;
    }

    public static String getXMLParam(Document doc, String tag, String ns)
    {
        NodeList elems = doc.getElementsByTagNameNS(ns, tag);
        if (elems != null && elems.getLength() > 0)
            return elems.item(0).getTextContent();

        return null;
    }

    public static String getXMLParam(Document doc, String tag)
    {
        NodeList elems = doc.getElementsByTagName(tag);
        if (elems != null && elems.getLength() > 0)
            return elems.item(0).getTextContent();

        return null;
    }

    private static String getDocumentAsString(Document document)
    {
        try(StringWriter outWriter = new StringWriter())
        {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.transform(new DOMSource(document), new StreamResult(outWriter));

            return outWriter.toString();
        }
        catch (TransformerFactoryConfigurationError | TransformerException e)
        {

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return "failed";
    }

}
