package com.example.lenovo.appnews.Object;

import android.text.TextUtils;
import android.util.Log;

import com.example.lenovo.appnews.Untils.Constans;
import com.example.lenovo.appnews.Untils.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLDOMParser {
    private static XMLDOMParser mInstance;
    private Logger mLogger = new Logger(XMLDOMParser.class.getSimpleName());

    public static XMLDOMParser getInstance() {
        if (mInstance == null) {
            mInstance = new XMLDOMParser();
        }
        return mInstance;
    }

    public Document getDocument(String xml) {
        Document document = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = factory.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            is.setEncoding("UTF-8");
            document = db.parse(is);
        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage(), e);
            return null;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage(), e);
            return null;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage(), e);
            return null;
        }
        return document;
    }

    private String getValue(Element item, String name) {
        NodeList nodes = item.getElementsByTagName(name);
        return this.getTextNodeValue(nodes.item(0));
    }

    private final String getTextNodeValue(Node elem) {
        Node child;
        if (elem != null) {
            if (elem.hasChildNodes()) {
                for (child = elem.getFirstChild(); child != null; child = child.getNextSibling()) {
                    if (child.getNodeType() == Node.TEXT_NODE) {
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }

    public RSSItems parseXMLToRSSItems(String xmlContent, Newpaper newpaper) {
        RSSItems rssItems = new RSSItems();
        if (TextUtils.isEmpty(xmlContent)) {
            return rssItems;
        }
        XMLDOMParser parser = new XMLDOMParser();
        try {
            Document document = parser.getDocument(xmlContent);
            NodeList nodeList = document.getElementsByTagName("item");
            NodeList nodeListDescription = document.getElementsByTagName("description");
            String title = "";
            String link = "";
            String pathImage = "";
            String pubData = "";
            String dateTmp = "";
            long timeStamp = 0;
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                title = element.getElementsByTagName("title").item(0)
                        .getTextContent();
                try {
                    link = element.getElementsByTagName("link").item(0)
                            .getTextContent();
                    pubData = element.getElementsByTagName("pubDate").item(0)
                            .getTextContent();
                    dateTmp = pubData.trim();
                    dateTmp = dateTmp.replace("(GMT+7)", "+0700");
                    dateTmp = dateTmp.replace("GMT+7", "+0700");
                    DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
                    Date date = null;
                    Date dateNow = new Date();
                    try {
                        date = formatter.parse(dateTmp);
                    } catch (ParseException e) {
                        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        try {
                            date = formatter.parse(dateTmp);
                        } catch (ParseException e1) {
                            formatter = new SimpleDateFormat("dd/MM/yyyy zzz");
                            try {
                                date = formatter.parse(dateTmp);
                                if(date.after(dateNow)) {
                                    formatter = new SimpleDateFormat("MM/dd/yyyy zzz");
                                    date = formatter.parse(dateTmp);
                                }
                            } catch (ParseException e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                    timeStamp = date.getTime();
                    mLogger.d(pubData);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                String cdata = nodeListDescription.item(i + 1).getTextContent();
                cdata = cdata.replace("data-original", "src");
                Pattern p = Pattern.compile("<\\s*img\\s*[^>]+src\\s*=\\s*(['\"]?)(.*?)\\1");
                Matcher matcher = p.matcher(cdata);
                if (matcher.find()) {
                    pathImage = matcher.group(2);
                }

                if (newpaper.getIdNewpaper() == Constans.ID_PAPER_TINTHETHAO) {
                    pathImage =  link = element.getElementsByTagName("image").item(0)
                            .getTextContent();
                }
                rssItems.addRSSItem(new RSSItem(title, link, cdata, pathImage, pubData, newpaper, timeStamp));
            }
            mLogger.d(rssItems.getSize() + "");
        } catch (NullPointerException ex) {
            mLogger.e(ex.toString());
        }
        Collections.sort(rssItems.getRssItems());
        return rssItems;
    }
}

