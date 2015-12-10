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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mydrivesolutions.juniortest.MyDriveGalleryAdapter;
import com.mydrivesolutions.juniortest.R;
import com.mydrivesolutions.juniortest.model.GalleryItem;
import com.mydrivesolutions.juniortest.model.GalleryItemBuilder;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GalleryFragment extends Fragment {
    @Bind(R.id.gallery_list)
    RecyclerView mList;

    MyDriveGalleryAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);


        ButterKnife.bind(this, view);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mAdapter = new MyDriveGalleryAdapter(getContext());

        mList.setHasFixedSize(true);
        mList.setAdapter(mAdapter);
        mList.setLayoutManager(mLayoutManager);

        loadImages();

        return view;
    }

    private void loadImages() {
        ArrayList<GalleryItem> images = new ArrayList<>();
        images.add(
                new GalleryItemBuilder()
                        .setTitle("Image 1")
                        .setUrl("http://xyer.co/wallpaper/24/3/cool-wallpaper-background-hd.jpg")
                        .build()
        );
        images.add(
                new GalleryItemBuilder()
                        .setTitle("Image 2")
                        .setUrl("http://media02.hongkiat.com/abstract/abstract-background.jpg")
                        .build()
        );
        images.add(
                new GalleryItemBuilder()
                        .setTitle("Image 3")
                        .setUrl("http://wallpaperput.com/wp-content/uploads/2014/10/cool-water-drops-blue-wallpaper.jpg")
                        .build()
        );
        images.add(
                new GalleryItemBuilder()
                        .setTitle("Image 4")
                        .setUrl("http://41.media.tumblr.com/685d13522aad72841285a0334eefc162/tumblr_nwhoqdtNst1qkq7k9o1_1280.jpg")
                        .build()
        );
        mAdapter.setItemList(images);
    }
}
