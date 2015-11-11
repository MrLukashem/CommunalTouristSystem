package com.system.mrlukashem.utils;

import android.util.Log;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.*;

/**
 * Created by mrlukashem on 02.11.15.
 */
public class XmlManager {

    private XmlPullParser mXmlParser;

    private StringBuilder inputStreamToStringBuilder(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

        String line = bufferedReader.readLine();
        while(line != null) {
            stringBuilder.append(line);
            line = bufferedReader.readLine();
        }

        return stringBuilder;
    }

    private void initParser(InputStream inputStream) throws XmlPullParserException, IOException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(false);
        mXmlParser= factory.newPullParser();

        StringBuilder temp = inputStreamToStringBuilder(inputStream);
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < temp.length(); i++) {
            if(temp.charAt(i) != '\t' && temp.charAt(i) != '\r') {
                result.append(temp.charAt(i));
            }
        }

        String result_string = result.toString();
        mXmlParser.setInput(new StringReader(result_string));
    }

    public static XmlManager newXmlManager(InputStream inputStream) throws XmlPullParserException, IOException {
        return new XmlManager(inputStream);
    }

    public static XmlManager newXmlManager(String filePath) throws XmlPullParserException, IOException {
        return new XmlManager(filePath);
    }

    public XmlManager(InputStream inputStream) throws XmlPullParserException, IOException {
        initParser(inputStream);
    }

    public XmlManager(String filePath) throws XmlPullParserException, IOException {
        InputStream inputStream = new FileInputStream(filePath);
        initParser(inputStream);
    }

    public void fillDataContainer() throws XmlPullParserException, IOException {
        String name = "";
        String data;

        XmlContentContainer contentContainer =
                XmlContentContainer.getInstance();

        try {
            int xmlEvent = mXmlParser.getEventType();
            while (xmlEvent != XmlPullParser.END_DOCUMENT) {

                switch (xmlEvent) {
                    case XmlPullParser.START_TAG:
                        name = mXmlParser.getName();
                        Log.d("GetDataContainer:", "StartTag = " + name);
                        break;
                    case XmlPullParser.END_TAG:
                        Log.d("GetDataContainer:", "EndTag = " + name);
                        break;
                    case XmlPullParser.TEXT:
                        data = mXmlParser.getText();
                        Log.d("GetDataContainer:", "content->" + data);
                        contentContainer.pushData(NullChecker.isNull(name), NullChecker.isNull(data));
                        break;
                    case XmlPullParser.START_DOCUMENT:
                        Log.d("GetDataContainer:", "start document event!");
                        break;
                }

                xmlEvent = mXmlParser.next();
            }
        } catch(NullPointerException exc) {
            exc.printStackTrace();
            Log.d("getDataContainer", "Parsing error, null pointer exception.");
        }
    }
}
