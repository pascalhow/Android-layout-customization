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
package com.mydrivesolutions.juniortest.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageHelper {
    public static void setImage(Context context, ImageView imageView, String image_url) {
        Picasso.with(context)
                .load(image_url)
                .into(imageView);
    }
}
