/**
 * Created by James Coggan on 01/12/2015.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mydrivesolutions.juniortest.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mydrivesolutions.juniortest.R;
import com.mydrivesolutions.juniortest.model.BitmapDecoder;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import butterknife.Bind;
import butterknife.ButterKnife;

public class CameraFragment extends Fragment {

    private static final int CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE = 1;
    private final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 2;


    @Bind(R.id.imageView_camera)
    ImageView imageView_cameraPicture;

    @Bind(R.id.cameraButton)
    Button button_camera;

    @Bind(R.id.textView_camera)
    TextView textView_cameraLabel;

    private String pictureFilePath;
    private int bitmapWidth = 1000;
    private int bitmapHeight = 700;

    private String cameraImageFolderName = "JuniorTest";

    private BitmapDecoder bitmapDecoder;

    /**
     * This method decodes the sampled bitmap from a file
     *
     * @param path      Bitmap file path
     * @param reqWidth  The required width of the bitmap
     * @param reqHeight The required height of the bitmap
     * @return The resized bitmap
     */
    private static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) {

        //First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight) {
            inSampleSize = Math.round((float) height / (float) reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth) {
            inSampleSize = Math.round((float) width / (float) reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        ButterKnife.bind(this, view);

        //  Get the user to give necessary access to the camera
        getAppPermissions();

        //  Create a directory for the camera images
        CreateImageDirectory(cameraImageFolderName);

        return view;
    }

    /**
     * This method creates the folder to save the camera images
     * @param folderName
     */
    public void CreateImageDirectory(String folderName)
    {
        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + folderName);

        //  If folder does not exist then create the directory
        if(!folder.exists()) {
            folder.mkdir();
        }
    }

    /**
     * This method creates the dated file
     * @return  File with date formatted name
     */
    public File CreateDatedFile()
    {
        //  Get the device current date to make each picture name unique
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_ssmmHH");
        String formattedDate = dateFormat.format(calendar.getTime());

        //  Save captured image on SD card
        File file = new File(Environment.getExternalStorageDirectory() + File.separator
                + "JuniorTest" + File.separator + "IMG_" + formattedDate + ".jpg");

        //  Save the file path with current date and time
        pictureFilePath = file.getPath();

        return file;
    }

    /**
     * This method is called when the camera activity returns. This is where the image bitmap is set
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                //Get our saved file into a bitmap object
                File file = new File(pictureFilePath);

                //  We need to get the bitmap from the actual directory otherwise "data" will return a small thumbnail
                Bitmap bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), bitmapWidth, bitmapHeight);

                //  Update the ImageView with the bitmap
                imageView_cameraPicture.setImageBitmap(bitmap);

            } else {
                Toast.makeText(getActivity(), "Picture was not taken!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    /**
     * This method requests for the permissions needed for the Camera functionality to work
     */
    private void getAppPermissions() {
        final List<String> permissionsList = new ArrayList<>();

        //  Add the user permissions
        addPermission(permissionsList, Manifest.permission.CAMERA);
        addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE);
        addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionsList.size() > 0)
        {
            //  Ask for user permission for each ungranted permission needed by the camera
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return;
        }

        //  If app already has all necessary permissions then carry on
        button_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                //  Create timestamped file for captured images
                File file = CreateDatedFile();

                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

                //  Start the camera activity
                startActivityForResult(intent, CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    /**
     * This method adds the permission string to a permission list if they are not currently granted
     * @param permissionsList
     * @param permission
     */
    private void addPermission(List<String> permissionsList, String permission) {
        if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED)
        {
            permissionsList.add(permission);
        }
    }

    /**
     * Callback with the request from requestPermission(...)
     *
     * @param requestCode   The code referring to the permission requested
     * @param permissions   The list of permissions requested
     * @param grantResults  The result of the requested permissions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<>();

                // Initial
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);

                // Fill with results
                for (int i = 0; i < permissions.length; i++) {
                    perms.put(permissions[i], grantResults[i]);
                }

                // Check if all permissions have been granted
                if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                {

                    // All Permissions Granted so set the camera button onClickListener
                    button_camera.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                            //  Create timestamped file for captured images
                            File file = CreateDatedFile();

                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

                            //  Start the camera activity
                            startActivityForResult(intent, CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);
                        }
                    });
                } else {
                    // Permission Denied
                    Toast.makeText(getActivity(), "Some permissions are denied", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
