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
package com.mydrivesolutions.juniortest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mydrivesolutions.juniortest.model.GalleryItem;
import com.mydrivesolutions.juniortest.utils.ImageHelper;

import java.util.ArrayList;

public class MyDriveGalleryAdapter extends RecyclerView.Adapter<MyDriveGalleryAdapter.ViewHolder> {

    private ArrayList<GalleryItem> itemList;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        int itemType;
        TextView title;
        ImageView image;

        public ViewHolder(View itemView, int ViewType) {
            super(itemView);
            this.itemType = ViewType;
            title = (TextView) itemView.findViewById(R.id.gallery_adapter_item_title);
            image = (ImageView) itemView.findViewById(R.id.gallery_adapter_item_image);
        }
    }

    public MyDriveGalleryAdapter(Context context) {
        this.itemList = new ArrayList<>();
        this.context = context;
    }

    public void setItemList(ArrayList<GalleryItem> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public MyDriveGalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_adapter_item, parent, false);
        return new ViewHolder(v, viewType);
    }

    @Override
    public void onBindViewHolder(final MyDriveGalleryAdapter.ViewHolder holder, int position) {
        final GalleryItem item = itemList.get(position);
        holder.title.setText(item.getTitle());
        ImageHelper.setImage(context, holder.image, item.getUrl());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}