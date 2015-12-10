package com.mydrivesolutions.juniortest.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mydrivesolutions.juniortest.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by pascalh on 4/12/2015.
 */
public class ShareFragment extends Fragment {

    private String imageFolderName = "JuniorTest";
    List<File> m_fileList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container, false);

        ButterKnife.bind(this, view);

        //  Get the list of images from the specified folder directory
        File folderPath = new File(Environment.getExternalStorageDirectory() + File.separator + imageFolderName);
        m_fileList = GetFileList(folderPath);

        String shareBody = "Here is the share content body";
        String ShareSubject = "Subject Title";
        Share(ShareType.ANY, shareBody, ShareSubject, Uri.fromFile(m_fileList.get(0)));

        return view;
    }

    /**
     * This method shares text or images via the user's sharing apps
     *
     * @param type    Define the type of what is being shared: Text or Images
     * @param body    Description of what is being shared
     * @param subject Subject of what is being shared
     */
    private void Share(ShareType type, String body, String subject, Uri uri) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);

        switch (type) {
            case TEXT:
                sharingIntent.setType("text/plain");
                break;
            case IMAGE:
                sharingIntent.setType("image/jpeg");
                break;
            case ANY:
                sharingIntent.setType("*/*");
        }

        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    /**
     * This method gets the list of image paths from the JuniorTest folder
     * @return  The list of image paths from the JuniorTest folder
     */
    private List<File> GetFileList(File folderPath) {
        File folder = folderPath;
        List<File> fileList = new ArrayList<>();

        if (folder.exists()) {
            for (int i = 0; i < folder.listFiles().length; i++) {
                //  Add all file path into the fileList
                fileList.add(folder.listFiles()[i]);
            }
        }

        return fileList;
    }

    public enum ShareType {
        TEXT, IMAGE, ANY
    }
}
