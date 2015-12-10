package com.mydrivesolutions.juniortest.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by pascalh on 10/12/2015.
 * The BitmapDecoder class takes as parameters the file path from which we want to create a bitmap
 * Decodes it according to the requested width and height
 */
public class BitmapDecoder {
    private int width = 0;
    private int height = 0;
    private String path;

    public BitmapDecoder(String path, int reqWidth, int reqHeight)
    {
        this.width = reqWidth;
        this.height = reqHeight;
        this.path = path;
    }

    /**
     * This method decodes the sampled bitmap from a file
     *
     * @return The resized bitmap
     */
    public Bitmap decodeSampledBitmapFromFile() {

        //First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > this.height) {
            inSampleSize = Math.round((float) height / (float) this.height);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > this.width) {
            inSampleSize = Math.round((float) width / (float) this.width);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(this.path, options);
    }
}
