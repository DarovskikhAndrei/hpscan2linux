package com.dara.hpscan.internal.events.compdest;

import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.dara.hpscan.IRequestBodyProvider;

public final class RegisterRequestBuilder implements IRequestBodyProvider
{
    private String server;
    private String serverName;
    private String type;
    private Document m_document;

    public RegisterRequestBuilder()
    {
        server = "localhost";
        try
        {
            server = InetAddress.getLocalHost().getHostName();
        }
        catch (UnknownHostException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        serverName = server;
        type = "Network";
    }

    public String contentType()
    {
        return "text/xml";
    }

    public String getBody()
    {
        StringWriter outWriter = new StringWriter();
        try
        {
            Document document = buildXMLDocument();

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.transform(new DOMSource(document), new StreamResult(outWriter));
        }
        catch (ParserConfigurationException | TransformerFactoryConfigurationError | TransformerException e)
        {

        }

        return outWriter.toString();
    }

    private Document buildXMLDocument() throws ParserConfigurationException
    {
        DocumentBuilder builder;
        DocumentBuilderFactory builderFactory;

        builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);

        builder = builderFactory.newDocumentBuilder();

        m_document = builder.newDocument();
        m_document.setXmlStandalone(true);
        m_document.setXmlVersion("1.0");

        Element elementWalkupScanToCompDestination =
                m_document.createElementNS("http://www.hp.com/schemas/imaging/con/ledm/walkupscan/2010/09/28",
                        "WalkupScanToCompDestination");
        elementWalkupScanToCompDestination.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance",
                "xsi:schemaLocation",
                "http://www.hp.com/schemas/imaging/con/ledm/walkupscan/2010/09/28 WalkupScanToComp.xsd");
        m_document.appendChild(elementWalkupScanToCompDestination);

        elementWalkupScanToCompDestination.appendChild(getServer());
        elementWalkupScanToCompDestination.appendChild(getServerName());
        elementWalkupScanToCompDestination.appendChild(getType());

        return m_document;
    }

    private Element getServer()
    {
        Element elementHostname =
                m_document.createElementNS("http://www.hp.com/schemas/imaging/con/dictionaries/2009/04/06",
                        "Hostname");
        elementHostname.setTextContent(server);

        return elementHostname;
    }

    private Element getServerName()
    {
        Element elementName =
                m_document.createElementNS("http://www.hp.com/schemas/imaging/con/dictionaries/1.0/",
                        "Name");
        elementName.setTextContent(serverName);

        return elementName;
    }

    private Element getType()
    {
        Element elementLinkType =
                m_document.createElement("LinkType");
        elementLinkType.setTextContent(type);

        return elementLinkType;
    }
}
