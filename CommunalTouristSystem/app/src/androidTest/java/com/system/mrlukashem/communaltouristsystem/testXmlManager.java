package com.system.mrlukashem.communaltouristsystem;

import android.os.Environment;

import com.system.mrlukashem.utils.XmlContentContainer;
import com.system.mrlukashem.utils.XmlManager;

import junit.framework.TestCase;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by mrlukashem on 08.12.15.
 */
public class testXmlManager extends TestCase {

    private XmlManager mXmlManager = null;

    public void testCheckFilledContainer() throws Exception {
        InputStream inputStream = new FileInputStream(Environment.getExternalStorageDirectory().getPath() + "/xml_files/content.xml");

        assertNotNull(inputStream);

        mXmlManager = XmlManager.newXmlManager(inputStream);
        assertNotNull(mXmlManager);

        mXmlManager.fillDataContainer();

        XmlContentContainer xmlContentContainer = XmlContentContainer.getInstance();

        CommuneDescriptor descriptor =xmlContentContainer.getCommuneDesc();
        assertNotNull(descriptor);

        assertNotNull(descriptor.getCommunalPlaces());
        assertFalse(descriptor.getCommunalPlaces().isEmpty());

        assertFalse(descriptor.getHistory().isEmpty());
        assertFalse(descriptor.getMajor().isEmpty());
        assertFalse(descriptor.getName().isEmpty());
        assertFalse(descriptor.getOthers().isEmpty());
        assertFalse(descriptor.getPlacement().isEmpty());
    }

    public void testLoadFile() throws Exception {
        InputStream inputStream = new FileInputStream(Environment.getExternalStorageDirectory().getPath() + "/xml_files/content.xml");

        assertNotNull(inputStream);

        mXmlManager = XmlManager.newXmlManager(inputStream);
        assertNotNull(mXmlManager);

        mXmlManager.fillDataContainer();
    }
}
