package com.mydrivesolutions.juniortest.test;

import android.os.Environment;
import android.test.InstrumentationTestCase;

import com.mydrivesolutions.juniortest.fragments.AboutMeFragment;
import com.mydrivesolutions.juniortest.fragments.CameraFragment;
import com.mydrivesolutions.juniortest.utils.JsonUtil;

import java.io.File;

/**
 * Created by pascalh on 13/12/2015.
 */
public class Tests extends InstrumentationTestCase {

    CameraFragment cameraFragment = new CameraFragment();

    /**
     * Test Method
     * Camera Fragment
     * This test method tests whether a folder is actually created when calling CreateImageDirectory()
     */
    public void testCameraFragment_CreateImageDirectory() throws Exception
    {
        String folderName = "TestFolder";

        cameraFragment.CreateImageDirectory(folderName);

        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + folderName);

        //  If folder does not exist then create the directory
        if (!folder.exists()) {
            folder.mkdir();
        }

        //  If folder was already created, mkdir() should return false
        assertFalse(folder.mkdir());
    }

    /**
     * Test Method
     * AboutMeFragment
     * This test method tests whether the string content of the json file contains key input strings
     */
    public void testAboutMeFragment_LoadProfile()
    {
        boolean jsonFileIsValid = false;

        AboutMeFragment aboutme = new AboutMeFragment();

        String actualProfile = JsonUtil.toJSon(aboutme.LoadProfile());

        if((actualProfile.contains("Pascal")) && (actualProfile.contains("How")) && (actualProfile.contains("Sesame"))
            && (actualProfile.contains("London")) && (actualProfile.contains("NW1 8UP")) && (actualProfile.contains("yourEmail@hotmail.com"))
                &&  (actualProfile.contains("999")))
        {
            jsonFileIsValid = true;
        }

        assertTrue(jsonFileIsValid);
    }
}
